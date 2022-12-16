package com.grupocinco.kilosapi.dto.view;

import com.grupocinco.kilosapi.service.DestinatarioService;
import org.springframework.beans.factory.annotation.Autowired;

public class DestinatarioViews {
    @Autowired
    private DestinatarioService destServ;
    public static class DestinatarioList{
    }
    public static class DestinatarioConcreto{
    }
    public static class ModeloPostDestinatario{
    }
    public static class DestinatarioConcretoDetalles extends  DestinatarioConcreto{

    }
}
