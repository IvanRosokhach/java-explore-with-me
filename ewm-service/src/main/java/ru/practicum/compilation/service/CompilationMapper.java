package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.service.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    private CompilationMapper() {
    }

    public static Compilation fromNewtoCompilation(NewCompilationDto compilationDto) {
        return Compilation.builder()
                .pinned(compilationDto.getPinned() == null ? false : compilationDto.getPinned())
                .title(compilationDto.getTitle())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(EventMapper.listToEventShortDto(compilation.getEvents()))
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static List<CompilationDto> listToCompilationDto(List<Compilation> compilations) {
        return compilations.stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }

}
