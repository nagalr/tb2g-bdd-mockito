package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by ronnen on 03-Mar-2021
 */

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    Owner savedOwner;

    @InjectMocks
    private OwnerController ownerController;

    Owner owner = new Owner(5L, "John", "Smith");

    @Test
    void processCreationFormHasErrors() {
        given(bindingResult.hasErrors()).willReturn(Boolean.TRUE);
        String result = ownerController.processCreationForm(owner, bindingResult);
        assertEquals("owners/createOrUpdateOwnerForm", result);
    }

    @Test
    void processCreationFormNoErrors() {
        // given
        given(bindingResult.hasErrors()).willReturn(Boolean.FALSE);
        given(ownerService.save(any(Owner.class))).willReturn(owner);

        // when
        String result = ownerController.processCreationForm(owner, bindingResult);

        // then
        assertThat(savedOwner).isNotNull();
        assertEquals("redirect:/owners/5", result);
        verify(ownerService, times(1)).save(any());
    }
}