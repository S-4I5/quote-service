package xyz.s4i5.quoteservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.s4i5.quoteservice.model.dto.CallbackDto;
import xyz.s4i5.quoteservice.service.CallbackService;

@RestController
@RequiredArgsConstructor
@RequestMapping(CallbackController.ROOT_URI)
public class CallbackController {
    protected static final String ROOT_URI = "/callback";
    protected static final String PROCESS_URI = "/{id}";

    private final CallbackService callbackService;

    @PostMapping(PROCESS_URI)
    public String processCallback(
            @RequestBody @Valid CallbackDto dto,
            @PathVariable String id
    ) {
        return callbackService.process(dto, id);
    }
}