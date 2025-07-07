package com.votacion.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@RestController
public class ExcelController {
    private final Map<String, String> partidos = new HashMap<>();
    private final List<Map<String, String>> candidatos = new ArrayList<>();

    @PostMapping("/subir-partidos")
    public String subirPartidos(@RequestParam("file") MultipartFile file) throws Exception {
        partidos.clear();
        XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(convert(file)));
        XSSFSheet sheet = workbook.getSheetAt(0);
        new File("../react-excel-app/public/tmp/iconos").mkdirs();
        Map<Integer, String> imagenes = extraerImagenes(sheet, "tmp/iconos/icono_");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row r = sheet.getRow(i);
            if (r != null) {
                String partido = r.getCell(0).getStringCellValue();
                String icono = imagenes.getOrDefault(i, "");
                partidos.put(partido.trim(), icono);
            }
        }
        workbook.close();
        return "Partidos cargados";
    }

    @PostMapping("/subir-candidatos")
    public String subirCandidatos(@RequestParam("file") MultipartFile file) throws Exception {
        candidatos.clear();
        List<String> errores = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(convert(file)));
        XSSFSheet sheet = workbook.getSheetAt(0);
        new File("../react-excel-app/public/tmp/fotos").mkdirs();
        Map<Integer, String> imagenes = extraerImagenes(sheet, "tmp/fotos/foto_");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row r = sheet.getRow(i);
            if (r != null) {
                String nombres = r.getCell(0).getStringCellValue();
                String apellidos = r.getCell(1).getStringCellValue();
                String partido = r.getCell(3).getStringCellValue();
                String foto = imagenes.getOrDefault(i, "");

                Map<String, String> c = new HashMap<>();
                c.put("nombres", nombres);
                c.put("apellidos", apellidos);
                c.put("foto", foto);
                c.put("partido", partido);

                if (partidos.containsKey(partido)) {
                    c.put("icono", partidos.get(partido));
                } else {
                    errores.add(partido);
                    c.put("icono", "");
                }

                candidatos.add(c);
            }
        }

        FileWriter fw = new FileWriter("../react-excel-app/public/tmp/candidatos.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(candidatos, fw);
        fw.close();
        workbook.close();

        if (!errores.isEmpty()) {
            return "Error: partidos no encontrados: " + errores.toString();
        }

        return "Candidatos procesados";
    }

    private Map<Integer, String> extraerImagenes(XSSFSheet sheet, String prefijo) throws IOException {
        Map<Integer, String> mapa = new HashMap<>();
        int contador = 1;
        for (POIXMLDocumentPart dr : sheet.getRelations()) {
            if (dr instanceof XSSFDrawing drawing) {
                for (XSSFShape shape : drawing.getShapes()) {
                    if (shape instanceof XSSFPicture pic) {
                        int row = pic.getClientAnchor().getRow1();
                        String nombre = prefijo + contador + ".png";
                        FileOutputStream out = new FileOutputStream("../react-excel-app/public/" + nombre);
                        out.write(pic.getPictureData().getData());
                        out.close();
                        mapa.put(row, nombre);
                        contador++;
                    }
                }
            }
        }
        return mapa;
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
