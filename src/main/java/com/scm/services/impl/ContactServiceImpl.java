package com.scm.services.impl;

import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repsitories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    // Save a new contact
    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    // Update an existing contact
    @Override
    public Contact update(Contact contact) {
        Contact contactOld = contactRepo.findById(contact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
        return contactRepo.save(contactOld);
    }

    // Get all contacts
    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    // Get contact by ID
    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
    }

    // Delete a contact
    @Override
    public void delete(String id) {
        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
        contactRepo.delete(contact);
    }

    // Get contacts by user ID
    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    // Get contacts by user with pagination and sorting
    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user, pageable);
    }

    // Search contacts by name
    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    // Search contacts by email
    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    // Search contacts by phone number
    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }

    // Count total contacts for a user
    @Override
    public long countTotalContacts(User user) {
        return contactRepo.countByUser(user);
    }

    // Count favorite contacts for a user
    @Override
    public long countFavoriteContacts(User user) {
        return contactRepo.countByUserAndFavorite(user, true);
    }

    // Count favorite contacts for a user (by method name)
    @Override
    public long countFavoriteContactsByUser(User user) {
        return contactRepo.countByUserAndFavorite(user, true);
    }
}
