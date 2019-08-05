package com.jwtsample.models;
//package com.jwtsample.jwtsample.models;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "users", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {
//            "userId"
//        })
////        ,
////        @UniqueConstraint(columnNames = {
////            "email"
////        })
//})
//public class User{
//	@Id
//	@Column(name="userid")
//    private Long userId;
//	@Column(name="usertitle")
//    private String userTitle;
//    
//	public Long getUserId() {
//		return userId;
//	}
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//	public String getUserTitle() {
//		return userTitle;
//	}
//	public void setUserTitle(String userTitle) {
//		this.userTitle = userTitle;
//	}
//    
//    
//
////    private String accessId;
////
////    private int channelId;
////
////    @NotBlank
////    @Size(max = 100)
////    private String password;
////
////    @ManyToMany(fetch = FetchType.LAZY)
////    @JoinTable(name = "user_roles",
////            joinColumns = @JoinColumn(name = "user_id"),
////            inverseJoinColumns = @JoinColumn(name = "role_id"))
////    private Set<Role> roles = new HashSet<>();
////    
////    private boolean enabled;
////    private boolean accountNonLocked;
////    private boolean credentialsNonExpired;
////    private boolean accountNonExpired;
////
////    public User() {
////
////    }
////
////   
////
////    public User(String accessId, int channelId,
////			@NotBlank @Size(max = 100) String password, Set<Role> roles) {
////		this.accessId = accessId;
////		this.channelId = channelId;
////		this.password = password;
////		this.roles = roles;
////	}
////
////
////
////	public Long getId() {
////		return id;
////	}
////
////
////
////	public void setId(Long id) {
////		this.id = id;
////	}
////
////
////
////	public String getAccessId() {
////		return accessId;
////	}
////
////
////
////	public void setAccessId(String accessId) {
////		this.accessId = accessId;
////	}
////
////
////
////	public int getChannelId() {
////		return channelId;
////	}
////
////
////
////	public void setChannelId(int channelId) {
////		this.channelId = channelId;
////	}
////
////	public String getPassword() {
////        return password;
////    }
////
////    public void setPassword(String password) {
////        this.password = password;
////    }
////
////    public Set<Role> getRoles() {
////        return roles;
////    }
////
////    public void setRoles(Set<Role> roles) {
////        this.roles = roles;
////    }
////
////
////
////	public boolean isEnabled() {
////		return enabled;
////	}
////
////
////
////	public void setEnabled(boolean enabled) {
////		this.enabled = enabled;
////	}
////
////
////
////	public boolean isAccountNonLocked() {
////		return true;
////	}
////
////
////
////	public void setAccountNonLocked(boolean accountNonLocked) {
////		this.accountNonLocked = accountNonLocked;
////	}
////
////
////
////	public boolean isCredentialsNonExpired() {
////		return true;
////	}
////
////
////
////	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
////		this.credentialsNonExpired = credentialsNonExpired;
////	}
////
////
////
////	public boolean isAccountNonExpired() {
////		return true;
////	}
////
////
////
////	public void setAccountNonExpired(boolean accountNonExpired) {
////		this.accountNonExpired = accountNonExpired;
////	}
////    
//    
//}
