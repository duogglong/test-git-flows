package com.ndl.trustviec.utils.system;

import com.auth0.jwt.interfaces.Claim;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SystemContext {
    private String cif;
    private String token;
    private Map<String, Claim> claims; // for jwt claims
    private Map<String, Object> attributes = new HashMap<>(); // for custom attributes
}
