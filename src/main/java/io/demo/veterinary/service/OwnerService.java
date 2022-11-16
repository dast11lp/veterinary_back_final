package io.demo.veterinary.service;

import org.springframework.stereotype.Service;

import io.demo.veterinary.entity.Owner;
import io.demo.veterinary.repository.OwnerRepository;

@Service
public class OwnerService extends BaseService<Owner, OwnerRepository>{

}
