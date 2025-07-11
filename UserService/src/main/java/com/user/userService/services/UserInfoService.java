// package com.user.userService.services;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;

// import java.util.Set;
// import java.util.stream.Collectors;

// @Service
// public class UserInfoService {

//     public UserInfo getCurrentUserInfo() {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         if (auth == null || !(auth.getPrincipal() instanceof UserDetails)) {
//             return null;
//         }
//         UserDetails user = (UserDetails) auth.getPrincipal();
//         Set<String> roles = user.getAuthorities().stream()
//                 .map(a -> a.getAuthority())
//                 .collect(Collectors.toSet());
//         return new UserInfo(user.getUsername(), roles);
//     }

//     public static class UserInfo {
//         private String username;
//         private Set<String> roles;

//         public UserInfo(String username, Set<String> roles) {
//             this.username = username;
//             this.roles = roles;
//         }

//         public String getUsername() {
//             return username;
//         }

//         public Set<String> getRoles() {
//             return roles;
//         }
//     }
// }