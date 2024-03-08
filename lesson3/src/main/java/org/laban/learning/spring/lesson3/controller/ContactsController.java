package org.laban.learning.spring.lesson3.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson3.data.Contact;
import org.laban.learning.spring.lesson3.services.ContactsService;
import org.laban.learning.spring.lesson3.validation.ValidationGroup;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ContactsController {
    private final static String TYPE_ATTRIBUTE = "type";
    private final static String CONTACT_SAMPLE_ATTRIBUTE = "contactSample";
    private final static String ADD_PAGE_TYPE = "add";
    private final static String UPDATE_PAGE_TYPE = "update";

    private final ContactsService contactsService;

    @GetMapping("/")
    public String contacts(Model model) {
        model.addAttribute("contactList", contactsService.findAllContacts());
        return "contacts";
    }

    @GetMapping("/contact/add")
    public String addContact(Model model) {
        model.addAttribute(TYPE_ATTRIBUTE, ADD_PAGE_TYPE);
        model.addAttribute(CONTACT_SAMPLE_ATTRIBUTE, new ContactSample());
        return "addOrUpdateContact";
    }

    @PostMapping("/contact/add")
    public String createContact(@Valid ContactSample sample) {
        contactsService.addContact(sample);
        return "redirect:/";
    }

    @GetMapping("/contact/update")
    public String updateContact(@RequestParam @NotNull Long id, Model model) {
        var contact = contactsService.findContactById(id);
        if (contact == null) {
            return "redirect:/";
        }

        model.addAttribute(TYPE_ATTRIBUTE, UPDATE_PAGE_TYPE);
        model.addAttribute(CONTACT_SAMPLE_ATTRIBUTE, createSampleFromContact(contact));
        return "addOrUpdateContact";
    }

    @PostMapping("/contact/update")
    public String updateContact(
            @Validated(value = { ValidationGroup.Update.class, Default.class }) ContactSample sample
    ) {
        contactsService.updateContact(sample);
        return "redirect:/";
    }

    @ModelAttribute("contactSample")
    public ContactSample contactSample() {
        return new ContactSample();
    }

    @GetMapping("contact/delete")
    public String deleteContact(@RequestParam @NotNull Long id) {
        contactsService.deleteContactById(id);
        return "redirect:/";
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

    private ContactSample createSampleFromContact(Contact contact) {
        return ContactSample.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .build();
    }

}