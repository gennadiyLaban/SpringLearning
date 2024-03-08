package org.laban.learning.spring.lesson3.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson3.services.ContactsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ContactsController {
    private final ContactsService contactsService;

    @GetMapping("/")
    public String contacts(Model model) {
        model.addAttribute("contactList", contactsService.findAllContacts());
        return "contacts";
    }

    @GetMapping("contact/delete")
    public String deleteContact(@RequestParam @NotNull Long id) {
        contactsService.deleteContactById(id);
        return "redirect:/";
    }

    @GetMapping("/contact/add")
    public String addContact(Model model) {
        return "addcontact";
    }

    @PostMapping("/contact/add")
    public String createContact(@Valid ContactSample sample) {
        contactsService.addContact(sample);
        return "redirect:/";
    }

    @ModelAttribute("contactSample")
    public ContactSample contactSample() {
        return new ContactSample();
    }

    @ExceptionHandler
    public ModelAndView handleException(MethodArgumentNotValidException exception) {
        String errorMsg = exception.getBindingResult().getFieldErrors()
                .stream()
                .filter(fieldError -> StringUtils.isNotEmpty(fieldError.getDefaultMessage()))
                .findFirst()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .orElse(exception.getMessage());
        return new ModelAndView("400", Map.of("errMessage", errorMsg), HttpStatus.BAD_REQUEST);
    }

}