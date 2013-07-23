package com.cutty.bravo.components.demo.manager;

import org.springframework.stereotype.Service;


import com.cutty.bravo.components.demo.domain.Student;
import com.cutty.bravo.core.manager.BaseManager;

@Service("studentManager")
public class StudentManager extends BaseManager<Student>{

}
