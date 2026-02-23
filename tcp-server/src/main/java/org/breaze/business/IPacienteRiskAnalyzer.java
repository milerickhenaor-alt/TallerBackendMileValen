package org.breaze.business;

import java.util.List;

public interface IPacienteRiskAnalyzer {
    PacienteRiskResult analizar(List<String> virusDetectados);
}
