package Simple.University.System.demo.Controller;

import Simple.University.System.demo.Repository.StudentRepository;
import Simple.University.System.demo.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class testController {
    @Autowired
    StudentService studentRepository;

    @GetMapping("/{id}")
    Object test(@PathVariable("id") Long id){
        return studentRepository.getReport(id);
    }
}
