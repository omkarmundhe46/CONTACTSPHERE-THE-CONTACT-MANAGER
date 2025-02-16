package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {
    // Save contacts
    Contact save(Contact contact);

    // Update contact
    Contact update(Contact contact);

    // Get all contacts
    List<Contact> getAll();

    // Get contact by ID
    Contact getById(String id);

    // Get count of favorite contacts for a user
    long countFavoriteContactsByUser(User user);

    // Count total contacts for a user
    long countTotalContacts(User user);

    // Count favorite contacts for a user
    long countFavoriteContacts(User user);

    // Delete a contact
    void delete(String id);

    // Search contacts by name
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

    // Search contacts by email
    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    // Search contacts by phone number
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);

    // Get contacts by user ID
    List<Contact> getByUserId(String userId);

    // Get contacts by user (pagination)
    Page<Contact> getByUser(User user, int page, int size, String sortField, String sortDirection);
}
