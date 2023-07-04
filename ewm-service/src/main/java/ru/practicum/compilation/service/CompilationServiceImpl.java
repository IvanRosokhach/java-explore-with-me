package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dao.CompilationRepository;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.exception.NotFoundException;

import javax.validation.UnexpectedTypeException;
import java.util.Collections;
import java.util.List;

import static ru.practicum.Constant.NOT_FOUND_COMPILATION;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    private final EventRepository eventRepository;

    @Override
    public CompilationDto save(NewCompilationDto compilationDto) {
        if (compilationDto.getTitle() == null || compilationDto.getTitle().isBlank()) {
            throw new UnexpectedTypeException("The title cannot be null and blank");
        }
        Compilation compilation = CompilationMapper.fromNewtoCompilation(compilationDto);
        if (compilationDto.getEvents() == null) {
            compilation.setEvents(Collections.emptyList());
        } else {
            compilation.setEvents(eventRepository.findAllByIdIn(compilationDto.getEvents()));
        }
        Compilation saved = compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(saved);
    }

    @Override
    public CompilationDto update(long compId, NewCompilationDto compilationDto) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_COMPILATION, compId)));
        if (compilationDto.getTitle() != null) {
            compilation.setTitle(compilationDto.getTitle());
        }
        if (compilationDto.getPinned() != null) {
            compilation.setPinned(compilationDto.getPinned());
        }
        if (compilationDto.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllByIdIn(compilationDto.getEvents()));
        }
        Compilation saved = compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(saved);
    }

    @Override
    public void delete(long compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new NotFoundException(String.format(NOT_FOUND_COMPILATION, compId));
        }
        compilationRepository.deleteById(compId);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Compilation> allByPinned = compilationRepository.findAllByPinned(pinned, pageRequest);
        return CompilationMapper.listToCompilationDto(allByPinned);
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_COMPILATION, compId)));
        return CompilationMapper.toCompilationDto(compilation);
    }

}
