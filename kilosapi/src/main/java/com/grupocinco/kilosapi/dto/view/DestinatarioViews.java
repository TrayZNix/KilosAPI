package com.grupocinco.kilosapi.dto.view;

import com.grupocinco.kilosapi.service.DestinatarioService;
import org.springframework.beans.factory.annotation.Autowired;

public class DestinatarioViews {

    public static class ModeloPostDestinatario{
    }
    public static class DestinatarioList extends ModeloPostDestinatario{
    }
    public static class DestinatarioConcreto extends ModeloPostDestinatario{
    }
    public static class DestinatarioConcretoDetalles extends  DestinatarioConcreto{

    }
}
