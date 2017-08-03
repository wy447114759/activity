import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;

import java.util.List;

/**
 * Created by hywin on 2017/8/2.
 */
public class Test {

    private ProcessEngine processEngine;

    @Before
    public void beforTest() {
        processEngine = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml")
                .buildProcessEngine();
    }

    @org.junit.Test
    public void testDelpoyMent(){
        System.out.println("---------processEngine:"+processEngine);

        //创建流程
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment().name("审批流程")
                .addClasspathResource("HelloActiviti.bpmn").deploy();

        System.out.println("------发布的流程ID:"+deployment.getId());
        System.out.println("------发布的流程name:"+deployment.getName());
    }

    @org.junit.Test
    public void testProcessInstance(){
        String processDefinitionKey = "helloActiviti";

        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey);

        System.out.println("*****流程ID:"+processInstance.getId());
        System.out.println("*****流程实例ID:"+processInstance.getProcessInstanceId());
        System.out.println("*****流程默认ID:"+processInstance.getProcessDefinitionId());
        System.out.println("*****流程默认KEY:"+processInstance.getProcessDefinitionKey());
        System.out.println("*****流程默认NAME:"+processInstance.getProcessDefinitionName());
    }

    @org.junit.Test
    public void findPersonnelTaskList(){
        String assignee = "YuWang";
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();
        if(list != null && !list.isEmpty()){
            for (Task task: list
                 ) {
                System.out.println("#######################################");
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("任务的名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("流程实例:"+task.getProcessInstanceId());
                System.out.println("#######################################");
            }
        }
    }

    @org.junit.Test
    public void executeTask(){
         String taskId = "7504";
         processEngine.getTaskService().complete(taskId);
         System.out.println("完成任务:"+taskId);
    }

}
