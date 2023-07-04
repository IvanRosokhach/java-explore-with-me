package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/compilations")
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto save(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Admin. Save compilation: {}", newCompilationDto);
        return compilationService.save(newCompilationDto);
    }

    @PatchMapping("/{compId}")
    public CompilationDto update(@PathVariable long compId,
                                 @RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Admin. Update compilation with id: {}, {}", compId, newCompilationDto);
        return compilationService.update(compId, newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long compId) {
        log.info("Admin. Delete compilation with id: {}", compId);
        compilationService.delete(compId);
    }

}
