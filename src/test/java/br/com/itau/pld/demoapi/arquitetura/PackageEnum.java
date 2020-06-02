package br.com.itau.pld.demoapi.arquitetura;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.itau.pld.demoapi.arquitetura.LayersEnum.*;

public enum PackageEnum {

    CONTROLLER_PACKAGE("br.com.itau.pld.demoapi.controller..", "..controller..", CONTROLLER),

    SERVICE_PACKAGE("br.com.itau.pld.demoapi.service..", "..service..", SERVICE),

    REPOSITORY_DOMAIN_PACKAGE("br.com.itau.pld.demoapi.repository..", "..repository..", REPOSITORY),

    CONFIGURATION_INFRASTRUCTURE_PACKAGE("br.com.itau.pld.demoapi.config..", "..config..", CONFIGURATION);

    private String path;

    private String genericPath;

    private LayersEnum layer;

    PackageEnum(String path, String genericPath, LayersEnum layer) {
        this.path = path;
        this.genericPath = genericPath;
        this.layer = layer;
    }

    public static List<PackageEnum> byLayer(LayersEnum layer){
        return Arrays.stream(
		        PackageEnum.values()).filter(packageEnum -> packageEnum.getLayer().equals(layer)).collect(
		        Collectors.toList());
    }

    public static String[] pathsPerLayer(LayersEnum layer){
	return Arrays.stream(
			PackageEnum.values()).filter(packageEnum -> packageEnum.getLayer().equals(layer)).map(
			PackageEnum::getPath).toArray(size -> new String[size]);
    }

    public static String[] genericPathsPerLayer(LayersEnum layer){
        return Arrays.stream(
		        PackageEnum.values()).filter(packageEnum -> packageEnum.getLayer().equals(layer)).map(
		        PackageEnum::getGenericPath).toArray(size -> new String[size]);
    }

    public String getPath() {
	return path;
    }

    public String getGenericPath() {
        return genericPath;
    }

    public LayersEnum getLayer() {
	return layer;
    }
}
