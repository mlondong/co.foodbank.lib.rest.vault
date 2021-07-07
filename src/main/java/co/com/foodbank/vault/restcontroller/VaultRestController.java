package co.com.foodbank.vault.restcontroller;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import co.com.foodbank.contribution.dto.DetailContributionDTO;
import co.com.foodbank.contribution.dto.GeneralContributionDTO;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceException;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceIllegalArgumentException;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceNotAvailableException;
import co.com.foodbank.user.sdk.exception.SDKUserServiceException;
import co.com.foodbank.user.sdk.exception.SDKUserServiceIllegalArgumentException;
import co.com.foodbank.user.sdk.exception.SDKUserServiceNotAvailableException;
import co.com.foodbank.vault.dto.IVault;
import co.com.foodbank.vault.dto.VaultDTO;
import co.com.foodbank.vault.exception.VaultNotFoundException;
import co.com.foodbank.vault.v1.controller.VaultController;
import co.com.foodbank.vault.v1.model.Vault;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.vault.restcontroller
 *         31/05/2021
 */
@RestController
@RequestMapping(value = "/vault")
@Tag(name = "Vault", description = "the vault API")
@Validated
public class VaultRestController {


    @Autowired
    public VaultController controller;



    /**
     * Method to find all Vault.
     * 
     * @param _id
     * @return {@code ResponseEntity<Collection<IVault>>}
     * @throws VaultNotFoundException
     */
    @Operation(summary = "Find all vaults.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Vault found.",
                            content = {
                                    @Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Service not available.",
                            content = @Content),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request.", content = @Content)})
    @GetMapping(value = "findAll/")
    public ResponseEntity<Collection<IVault>> findById()
            throws VaultNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(controller.findAll());
    }



    /**
     * Method to find by Contact.
     * 
     * @param _id
     * @return {@code ResponseEntity<Collection<IVault>>}
     * @throws VaultNotFoundException
     */
    @Operation(summary = "Find Vault by contact.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Vault found.",
                            content = {
                                    @Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Service not available.",
                            content = @Content),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request.", content = @Content)})
    @GetMapping(value = "findContact/name/{name}")
    public ResponseEntity<Collection<IVault>> findByContact(
            @PathVariable("name") @NotNull @NotBlank String contact)
            throws VaultNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(controller.findByContact(contact));
    }



    /**
     * Method to find Vault by Dictrict.
     * 
     * @param _id
     * @return {@code ResponseEntity<Collection<IVault>>}
     * @throws VaultNotFoundException
     */
    @Operation(summary = "Find Vault by district.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Vault found.",
                            content = {
                                    @Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Service not available.",
                            content = @Content),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request.", content = @Content)})
    @GetMapping(value = "findDistrict/{name}")
    public ResponseEntity<Collection<IVault>> findByDistrict(
            @PathVariable("name") @NotNull @NotBlank String district)
            throws VaultNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(controller.findByDistrict(district));
    }



    /**
     * Method to create a Vault.
     * 
     * @param dto
     * @return {@code ResponseEntity<IVault>}
     * @throws VaultNotFoundException
     */
    @Operation(summary = "Create a Vault", description = "", tags = {"vault"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Vault created",
                            content = @Content(schema = @Schema(
                                    implementation = Vault.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input"),
                    @ApiResponse(responseCode = "409",
                            description = "Vault already exists")})
    @PostMapping(value = "/createVault",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IVault> createVAult(@RequestBody @Valid VaultDTO dto)
            throws VaultNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(controller.createVault(dto));
    }



    /**
     * Method to update a Vault.
     * 
     * @param dto
     * @return {@code ResponseEntity<IVault>}
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @Operation(summary = "Update general information in  Vault",
            description = "", tags = {"vault"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Vault updated",
                            content = @Content(schema = @Schema(
                                    implementation = Vault.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input"),
                    @ApiResponse(responseCode = "409",
                            description = "Vault already exists")})
    @PutMapping(value = "/updateVault/id/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IVault> updateVault(
            @PathVariable("id") @NotBlank @NotNull String _id,
            @RequestBody @Valid VaultDTO dto)
            throws VaultNotFoundException, JsonMappingException,
            JsonProcessingException, SDKUserServiceNotAvailableException,
            SDKUserServiceException, SDKUserServiceIllegalArgumentException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(controller.updateVault(dto, _id));
    }



    /**
     * Method to find Vault.
     * 
     * @param _id
     * @return {@code IVault}
     * @throws VaultNotFoundException
     */
    @Operation(summary = "Find vault by id.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Vault found.",
                            content = {
                                    @Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Service not available.",
                            content = @Content),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request.", content = @Content)})
    @GetMapping(value = "findById/id/{id}")
    public IVault findById(@PathVariable("id") @NotBlank @NotNull String _id)
            throws VaultNotFoundException {
        return controller.findById(_id);
    }



    /**
     * Method to add Contributions in Vault.
     * 
     * @param _id
     * @return {@code ResponseEntity<IVault> }
     * @throws SDKContributionServiceIllegalArgumentException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     */

    @Operation(summary = "Add Detail-Contributions in Vault", description = "",
            tags = {"contribution"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Vault updated",
                            content = @Content(schema = @Schema(
                                    implementation = Vault.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input"),
                    @ApiResponse(responseCode = "409",
                            description = "Vault already exists")})
    @PostMapping(value = "/add-DetailContribution/vault-id/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IVault> addContributionDetailInVault(
            @RequestBody @Valid DetailContributionDTO dto,
            @PathVariable("id") @NotBlank @NotNull String _id)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException,
            SDKUserServiceNotAvailableException, SDKUserServiceException,
            SDKUserServiceIllegalArgumentException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(controller.addDetailContributionInVault(dto, _id));
    }


    /**
     * Method to add Contributions in Vault.
     * 
     * @param _id
     * @return {@code ResponseEntity<IVault> }
     * @throws SDKContributionServiceIllegalArgumentException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     */
    @Operation(summary = "Add General-Contributions in Vault", description = "",
            tags = {"contribution"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Vault updated",
                            content = @Content(schema = @Schema(
                                    implementation = Vault.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input"),
                    @ApiResponse(responseCode = "409",
                            description = "Vault already exists")})
    @PostMapping(value = "/add-GeneralContribution/vault-id/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IVault> addContributionGeneralInVault(
            @RequestBody @Valid GeneralContributionDTO dto,
            @PathVariable("id") @NotBlank @NotNull String _id)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException,
            SDKUserServiceNotAvailableException, SDKUserServiceException,
            SDKUserServiceIllegalArgumentException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(controller.addGeneralContributionInVault(dto, _id));
    }


}
