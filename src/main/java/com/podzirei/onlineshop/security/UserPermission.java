package com.podzirei.onlineshop.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserPermission {
    ADMIN_ADD("admin:admin");

    private final String permission;
}
