package org.engripaye.secureinternaladminportal.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserAdminService {

    private final UserService userService;


    public UserAdminService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Disable a user account.
     * Only allowed if the authenticated user has PERM_USER_EDIT.
     */

    @PreAuthorize("hasAuthority('PERM_USER_EDIT')")
    public void disableUser(Long id){
        // Call your existing userService method (you'll need to implement disable logic there)// call ur existing database

        userService.disableUser(id);
    }

    /**
     * Delete a user account.
     * Only allowed if the authenticated user has PERM_USER_DELETE.
     */

    @PreAuthorize("hasAuthority('PERM_USER_DELETE')")
    public void deleteUser(Long id){
        // Call delete logic in userService (to be implemented)
        userService.deleteUser(id);
    }


    /**
     * View user details.
     * Only allowed if the authenticated user has PERM_USER_VIEW.
     */

    @PreAuthorize("hasAuthority('PREM_USER_VIEW')")
    public Object getUserDetails(Long id){
        return userService.getUserById(id);
    }

}
