package mailTask.page;


import mailTask.element.MailListForm;
import mailTask.element.TypeMailForm;

public class MailListPage {
    public TypeMailForm onTypeMailForm() {
        return new TypeMailForm();
    }

    public MailListForm onMailListForm() {
        return new MailListForm();
    }
}
