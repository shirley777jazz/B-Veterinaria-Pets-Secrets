package com.PetsSecrets.Veterinaria_Backend.config;

import com.PetsSecrets.Veterinaria_Backend.dtos.CategoriaRequest;
import com.PetsSecrets.Veterinaria_Backend.dtos.ProductoRequest;
import com.PetsSecrets.Veterinaria_Backend.models.Sede;
import com.PetsSecrets.Veterinaria_Backend.models.Servicio;
import com.PetsSecrets.Veterinaria_Backend.models.Veterinario;
import com.PetsSecrets.Veterinaria_Backend.repositories.SedeRepository;
import com.PetsSecrets.Veterinaria_Backend.repositories.ServicioRepository;
import com.PetsSecrets.Veterinaria_Backend.repositories.VeterinarioRepository;
import com.PetsSecrets.Veterinaria_Backend.services.CategoriaService;
import com.PetsSecrets.Veterinaria_Backend.services.ProductoService;
import com.PetsSecrets.Veterinaria_Backend.services.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private ProductoService productoService;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario administrador por defecto
        usuarioService.crearUsuarioAdminSiNoExiste();
        
        // Inicializar datos de prueba
        inicializarDatosDePrueba();
    }
    
    private void inicializarDatosDePrueba() {
        try {
            // Verificar si ya existen categorías
            if (categoriaService.obtenerTodasLasCategorias().isEmpty()) {
                
                // Crear categorías
                CategoriaRequest alimento = CategoriaRequest.builder()
                        .nombre("Alimentos")
                        .descripcion("Alimentos para mascotas")
                        .build();
                var categoriaAlimento = categoriaService.crearCategoria(alimento);
                
                CategoriaRequest juguete = CategoriaRequest.builder()
                        .nombre("Juguetes")
                        .descripcion("Juguetes para mascotas")
                        .build();
                var categoriaJuguete = categoriaService.crearCategoria(juguete);
                
                CategoriaRequest accesorio = CategoriaRequest.builder()
                        .nombre("Accesorios")
                        .descripcion("Accesorios para mascotas")
                        .build();
                var categoriaAccesorio = categoriaService.crearCategoria(accesorio);
                
                // Crear productos de ejemplo
                ProductoRequest producto1 = ProductoRequest.builder()
                        .nombre("Alimento Premium para Perros")
                        .descripcion("Alimento balanceado premium para perros adultos")
                        .precio(45.90)
                        .stock(100)
                        .imagenUrl("assets/productos/producto1.webp")
                        .estado("activo")
                        .categoriaId(categoriaAlimento.getId())
                        .build();
                productoService.crearProducto(producto1);
                
                ProductoRequest producto2 = ProductoRequest.builder()
                        .nombre("Pelota de Goma")
                        .descripcion("Pelota resistente para juegos de perros")
                        .precio(15.50)
                        .stock(50)
                        .imagenUrl("assets/productos/producto4.webp")
                        .estado("activo")
                        .categoriaId(categoriaJuguete.getId())
                        .build();
                productoService.crearProducto(producto2);
                
                ProductoRequest producto3 = ProductoRequest.builder()
                        .nombre("Collar Ajustable")
                        .descripcion("Collar ajustable con hebilla de seguridad")
                        .precio(25.00)
                        .stock(30)
                        .imagenUrl("assets/productos/producto2.webp")
                        .estado("activo")
                        .categoriaId(categoriaAccesorio.getId())
                        .build();
                productoService.crearProducto(producto3);
                
                System.out.println("Datos de productos inicializados correctamente");
            }

            // Inicializar veterinarios, sedes y servicios si están vacíos
            if (veterinarioRepository.count() == 0) {
                Veterinario v1 = Veterinario.builder()
                        .nombreCompleto("Dr. Juan Pérez")
                        .especialidad("Medicina General")
                        .telefono("123456789")
                        .email("juan.perez@veterinaria.com")
                        .build();
                Veterinario v2 = Veterinario.builder()
                        .nombreCompleto("Dra. María García")
                        .especialidad("Cirugía")
                        .telefono("987654321")
                        .email("maria.garcia@veterinaria.com")
                        .build();
                Veterinario v3 = Veterinario.builder()
                        .nombreCompleto("Dr. Carlos López")
                        .especialidad("Cardiología")
                        .telefono("456789123")
                        .email("carlos.lopez@veterinaria.com")
                        .build();

                veterinarioRepository.save(v1);
                veterinarioRepository.save(v2);
                veterinarioRepository.save(v3);
                System.out.println("Veterinarios inicializados correctamente");
            }

            if (sedeRepository.count() == 0) {
                Sede s1 = Sede.builder()
                        .nombre("Sede Mariscal")
                        .direccion("Av. Mariscal Castilla 123, SJL")
                        .telefono("01-234-5678")
                        .ciudad("Lima")
                        .build();
                Sede s2 = Sede.builder()
                        .nombre("Sede Paradita")
                        .direccion("Jr. La Paradita 456, SJL")
                        .telefono("01-345-6789")
                        .ciudad("Lima")
                        .build();
                Sede s3 = Sede.builder()
                        .nombre("Sede Jicamarca")
                        .direccion("Av. Jicamarca 789, SJL")
                        .telefono("01-456-7890")
                        .ciudad("Lima")
                        .build();

                sedeRepository.save(s1);
                sedeRepository.save(s2);
                sedeRepository.save(s3);
                System.out.println("Sedes inicializadas correctamente");
            }

            if (servicioRepository.count() == 0 && veterinarioRepository.count() > 0) {
                List<Veterinario> vets = veterinarioRepository.findAll();
                Veterinario v1 = vets.get(0);
                Veterinario v2 = vets.size() > 1 ? vets.get(1) : v1;
                Veterinario v3 = vets.size() > 2 ? vets.get(2) : v1;

                servicioRepository.save(Servicio.builder().nombre("Consulta Médica General").descripcion("Consulta veterinaria general para diagnóstico y chequeo").precio(80.00).veterinario(v1).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Vacunación").descripcion("Aplicación de vacunas según calendario veterinario").precio(50.00).veterinario(v1).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Desparasitación").descripcion("Tratamiento antiparasitario interno y externo").precio(40.00).veterinario(v1).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Cirugía Menor").descripcion("Procedimientos quirúrgicos menores ambulatorios").precio(300.00).veterinario(v2).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Cirugía Mayor").descripcion("Procedimientos quirúrgicos complejos con hospitalización").precio(800.00).veterinario(v2).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Esterilización").descripcion("Cirugía de esterilización para machos y hembras").precio(250.00).veterinario(v2).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Consulta Cardiológica").descripcion("Evaluación especializada del sistema cardiovascular").precio(150.00).veterinario(v3).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Electrocardiograma").descripcion("Estudio del ritmo cardíaco mediante electrocardiograma").precio(120.00).veterinario(v3).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Ecografía").descripcion("Diagnóstico por imágenes ecográficas").precio(100.00).veterinario(v1).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Análisis de Sangre").descripcion("Exámenes de laboratorio básicos").precio(60.00).veterinario(v1).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Radiografía").descripcion("Estudios radiológicos para diagnóstico").precio(80.00).veterinario(v1).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Limpieza Dental").descripcion("Profilaxis dental veterinaria").precio(180.00).veterinario(v1).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Emergencias 24h").descripcion("Atención de emergencias las 24 horas").precio(200.00).veterinario(v1).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Internamiento").descripcion("Hospitalización y cuidados intensivos por día").precio(150.00).veterinario(v1).activo(true).build());
                servicioRepository.save(Servicio.builder().nombre("Baño y Corte").descripcion("Servicio de peluquería y aseo para mascotas").precio(35.00).veterinario(v1).activo(true).build());

                System.out.println("Servicios inicializados correctamente");
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar datos de prueba: " + e.getMessage());
        }
    }
}