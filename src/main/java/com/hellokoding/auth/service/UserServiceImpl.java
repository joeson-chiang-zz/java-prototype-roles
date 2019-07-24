package com.hellokoding.auth.service;

import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.RoleBinding;
import com.hellokoding.auth.model.RoleBindingEntitlement;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.RoleBindingEntitlementRepository;
import com.hellokoding.auth.repository.RoleBindingRepository;
import com.hellokoding.auth.repository.RoleRepository;
import com.hellokoding.auth.repository.UserRepository;
import com.hellokoding.auth.util.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleBindingRepository roleBindingRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleBindingEntitlementRepository roleBindingEntitlementRepository;

    @Override
    public void save(User user) {
        Role role = roleRepository.findByName("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

        RoleBinding roleBinding = new RoleBinding();
        roleBinding.setRoleId(role.getId());
        roleBinding.setUserId(user.getId());
        roleBindingRepository.save(roleBinding);


        RoleBindingEntitlement entitlement = new RoleBindingEntitlement();
        entitlement.setResourceType(ResourceType.FLOW);
        entitlement.setInstanceIds(Arrays.asList((long) 1));
        roleBindingEntitlementRepository.save(entitlement);

        System.out.println("Role binding " + roleBinding.getId() + " roleId " + roleBinding.getRoleId());
        System.out.println("For user " + user.getUsername());

        System.out.println("Role binding entitlement instances " + entitlement.getInstanceIds().toString());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
