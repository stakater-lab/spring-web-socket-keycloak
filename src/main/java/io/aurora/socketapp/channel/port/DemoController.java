package io.aurora.socketapp.channel.port;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DemoController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/hello")
    public String sayHelloToAdmin(final Principal principal)
    {
        return "{\"msg\":\"Hello Admin: "+ principal.getName()+"\"}";
    }

    @Secured("ROLE_USER")
    @GetMapping("/user/hello")
    public String sayHelloToUser(final Principal principal)
    {
        return "{\"msg\":\"Hello User: "+ principal.getName()+"\"}";
    }

    @GetMapping("/guest/hello")
    public String sayHelloToGuest() {
        return "{\"msg\":\"Hello World\"}";
    }
}
