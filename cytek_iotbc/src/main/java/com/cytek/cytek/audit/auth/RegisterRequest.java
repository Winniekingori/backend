package com.cytek.cytek.audit.auth;
import com.cytek.cytek.audit.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String name;
  private String email;
  private String password;
  private Role role;
  private String phone;
  private String country;
  private String county;
  private String town;

}
