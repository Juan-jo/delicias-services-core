package org.delicias.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "delicias.supabase")
public interface SupabaseConfig {
    String url();
    String key();
    String bucket();
}
