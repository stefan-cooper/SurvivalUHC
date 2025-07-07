package com.stefancooper.SurvivalUHC.utils;

import com.stefancooper.SurvivalUHC.enums.ConfigKey;

public record Configurable<T>(ConfigKey key, T value) {}
