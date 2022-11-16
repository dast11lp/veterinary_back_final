package io.demo.veterinary.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.demo.veterinary.entity.Owner;
import io.demo.veterinary.service.OwnerService;

@RestController
@RequestMapping("owner")
@CrossOrigin("*")
public class OwnerController extends BaseController<Owner, OwnerService>{

}
