package com.mibarrio.api.service.email;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlatformEmailService {

    private final EmailService emailService;
    private final UsuarioRepository usuarioRepository;

    public void notifyAdminsOfPendingSignup(Usuario usuario) {
        String subject = "Nuevo comercio pendiente de aprobacion";
        String body = """
                <h3>Nuevo registro pendiente</h3>
                <p>Se ha registrado un nuevo comercio en miBarrio.</p>
                <ul>
                  <li><strong>Nombre:</strong> %s</li>
                  <li><strong>Email:</strong> %s</li>
                  <li><strong>Estado:</strong> %s</li>
                </ul>
                <p>Accede al panel de administracion para revisarlo.</p>
                """.formatted(usuario.getNombre(), usuario.getEmail(), usuario.getEstado().name());

        getAdminEmails().forEach(email -> emailService.enviarEmail(email, subject, body));
    }

    public void notifyAdminsOfPendingComercio(Comercio comercio) {
        Usuario usuario = comercio.getUsuario();
        String subject = "Nuevo comercio pendiente de aprobacion";
        String body = """
                <h3>Nueva solicitud de comercio</h3>
                <p>Se ha registrado un comercio pendiente de revision en miBarrio.</p>
                <ul>
                  <li><strong>Comercio:</strong> %s</li>
                  <li><strong>Direccion:</strong> %s</li>
                  <li><strong>Categoria:</strong> %s</li>
                  <li><strong>Gestor:</strong> %s</li>
                  <li><strong>Email:</strong> %s</li>
                </ul>
                <p>Accede al panel de administracion para aprobarlo o rechazarlo.</p>
                """.formatted(
                comercio.getNombre(),
                comercio.getDireccion(),
                comercio.getCategoria() != null ? comercio.getCategoria().getNombre() : "Sin categoria",
                usuario != null ? usuario.getNombre() : "Sin gestor",
                usuario != null ? usuario.getEmail() : "Sin email");

        getAdminEmails().forEach(email -> emailService.enviarEmail(email, subject, body));
    }

    public void sendSignupReceivedEmail(Usuario usuario) {
        String body = """
                <h3>Solicitud recibida</h3>
                <p>Hola %s, hemos recibido tu registro en miBarrio.</p>
                <p>Tu cuenta esta <strong>pendiente de aprobacion</strong> por parte de un administrador.</p>
                <p>Te avisaremos por email cuando se revise.</p>
                """.formatted(usuario.getNombre());
        emailService.enviarEmail(usuario.getEmail(), "Registro recibido en miBarrio", body);
    }

    public void sendApprovalEmail(Usuario usuario) {
        String body = """
                <h3>Cuenta aprobada</h3>
                <p>Hola %s, tu cuenta ha sido aprobada.</p>
                <p>Ya puedes iniciar sesion y gestionar tu comercio en miBarrio.</p>
                """.formatted(usuario.getNombre());
        emailService.enviarEmail(usuario.getEmail(), "Tu cuenta ha sido aprobada", body);
    }

    public void sendRejectionEmail(Usuario usuario) {
        String body = """
                <h3>Cuenta rechazada</h3>
                <p>Hola %s, tu solicitud de acceso ha sido rechazada.</p>
                <p>Si crees que se trata de un error, contacta con administracion.</p>
                """.formatted(usuario.getNombre());
        emailService.enviarEmail(usuario.getEmail(), "Tu cuenta no ha sido aprobada", body);
    }

    public void sendComercioEventEmail(Usuario usuario, String subject, String description) {
        String body = """
                <h3>%s</h3>
                <p>%s</p>
                """.formatted(subject, description);
        emailService.enviarEmail(usuario.getEmail(), subject, body);
    }

    private List<String> getAdminEmails() {
        return usuarioRepository.findByRolNombreAndActivoTrue("ADMIN")
                .stream()
                .map(Usuario::getEmail)
                .toList();
    }
}
