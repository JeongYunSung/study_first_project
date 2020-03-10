package com.yunseong.first_project.controller;

import com.yunseong.first_project.controller.errors.MemberSearchValidator;
import com.yunseong.first_project.controller.errors.PageableValidator;
import com.yunseong.first_project.dto.MemberDto;
import com.yunseong.first_project.dto.MemberSearchCondition;
import com.yunseong.first_project.dto.model.MemberDtoModel;
import com.yunseong.first_project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/members", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberSearchValidator memberSearchValidator;
    @Autowired
    private PageableValidator pageableValidator;

/*    public MemberController() {
         = new WebMvcLinkBuilderFactory();
        .setUriComponentsContributors(Arrays.asList(new HateoasPageableHandlerMethodArgumentResolver()));
    }*/

    //서치에서 발견못했을경우 페이지에러

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleNoSuchElementException(Exception exception) {
        Errors errors = new BeanPropertyBindingResult(-1L,"id");
        errors.reject("wrongValue", "id is wrongValue");
        return ResponseEntity.badRequest().body(errors);
    }

   @GetMapping("/{id}")
    public ResponseEntity getMember(@PathVariable Long id) {
        MemberDtoModel memberDtoModel = new MemberDtoModel(this.memberService.findMemberById(id));
        memberDtoModel.add(linkTo(MemberController.class).slash(id).withSelfRel());
        memberDtoModel.add(linkTo(MemberController.class).withRel("members"));
        return ResponseEntity.ok(memberDtoModel);
    }

    @GetMapping
    public ResponseEntity getMembers(@PageableDefault Pageable pageable) {
        Page<MemberDto> page = this.memberService.findAllByPage(pageable);
        Errors errors = new BeanPropertyBindingResult(pageable,"pageable");
        this.pageableValidator.validate(page, pageable, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok(getMembersModel(page));
/*        return ResponseEntity.ok(getMembersModel(page,
                () -> linkTo(methodOn(MemberController.class).getMembers(PageRequest.of(pageable.getPageNumber() + 1, pageable.getPageSize(), pageable.getSort()))),
                () -> linkTo(methodOn(MemberController.class).getMembers(PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort())))));*/
    }

    @GetMapping("/search")
    public ResponseEntity getMembersBySearch(@PageableDefault Pageable pageable, MemberSearchCondition condition, Errors errors) {
        Page<MemberDto> page = this.memberService.findPageBySearch(condition, pageable);
        this.pageableValidator.validate(page, pageable, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        this.memberSearchValidator.validate(condition, errors);
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Map<String, String> query = new HashMap<>();
        query.put("username", condition.getUsername());
        query.put("nickname", condition.getNickname());
        query.put("teamname", condition.getTeamname());
        return ResponseEntity.ok(getMembersModel(page, "/search", query));
/*        return ResponseEntity.ok(getMembersModel(page,
                builder ->
                    builder.
                            queryParam("username", condition.getUsername()).
                            queryParam("nickname", condition.getNickname()).
                            queryParam("teamname", condition.getTeamname())
                            .build()
                ));*/
/*        return ResponseEntity.ok(getMembersModel(page,
                () -> linkTo(methodOn(MemberController.class).getMembersBySearch(PageRequest.of(pageable.getPageNumber() + 1, pageable.getPageSize(), pageable.getSort()), condition, errors)),
                () -> linkTo(methodOn(MemberController.class).getMembersBySearch(PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()), condition, errors))));*/
    }

    private PagedModel<MemberDto> getMembersModel(Page<MemberDto> pages) {
        return getMembersModel(pages, "",null);
    }

    private PagedModel<MemberDto> getMembersModel(Page<MemberDto> pages, String path, Map<String, String> query) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().path("http://localhost:8080/api/members" + path);
        if (query != null) {
            query.entrySet().stream().forEach(entry -> {
                if (entry.getValue() != null) {
                    uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
                }
            });
        }
        PagedResourcesAssembler assembler = new PagedResourcesAssembler(new HateoasPageableHandlerMethodArgumentResolver(), uriComponentsBuilder.build());
        PagedModel<MemberDto> pagedModel = assembler.toModel(pages);
        return pagedModel;
    }

/*    private PagedModel<MemberDto> getMembersModel(Page<MemberDto> pages, Function<UriComponentsBuilder, UriComponents> func) {
        PagedResourcesAssembler assembler = new PagedResourcesAssembler(new HateoasPageableHandlerMethodArgumentResolver(), func.apply(UriComponentsBuilder.newInstance().path("http://localhost:8080/api/members")));
        PagedModel<MemberDto> pagedModel = assembler.toModel(pages);
        return pagedModel;
    }*/

/*    private PagedModel<MemberDto> getMembersModel(Page<MemberDto> pages, Supplier<WebMvcLinkBuilder> next, Supplier<WebMvcLinkBuilder> previous) {
        PageMetadata pageMetadata = new PageMetadata(pages.getSize(), pages.getNumber(), pages.getTotalElements(), pages.getTotalPages());
        PagedModel<MemberDto> pagedModel = new PagedModel<>(pages.getContent(), pageMetadata);
        pagedModel.add(linkTo(MemberController.class).withSelfRel());
        if (pages.getNumber() <= (pages.getTotalPages()-1)) {
            if (pages.getNumber() != 0) {
                pagedModel.add(previous.get().withRel("previousPage"));
            }
            if (pages.getNumber() != (pages.getTotalPages()-1)) {
                pagedModel.add(next.get().withRel("nextPage"));
            }
        }
        return pagedModel;
    }*/
}
