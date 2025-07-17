import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PersonalTaskManagerViolations {

    private static final String DB_FILE_PATH = "tasks_database.json";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Phương thức trợ giúp để tải dữ liệu (sẽ được gọi lặp lại)
    private boolean isDuplicateTask(JSONArray tasks, String title, LocalDate dueDate) {
    for (Object obj : tasks) {
        JSONObject task = (JSONObject) obj;
        if (task.get("title").toString().equalsIgnoreCase(title) &&
            task.get("due_date").toString().equals(dueDate.format(DATE_FORMATTER))) {
            return true;
        }
    }
    return false;
}

    // Phương thức trợ giúp để lưu dữ liệu
    private static void saveTasksToDb(JSONArray tasksData) {
        try (FileWriter file = new FileWriter(DB_FILE_PATH)) {
            file.write(tasksData.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi vào file database: " + e.getMessage());
        }
    }

    /**
     * Chức năng thêm nhiệm vụ mới
     *
     * @param title Tiêu đề nhiệm vụ.
     * @param description Mô tả nhiệm vụ.
     * @param dueDateStr Ngày đến hạn (định dạng YYYY-MM-DD).
     * @param priorityLevel Mức độ ưu tiên ("Thấp", "Trung bình", "Cao").
     * @param isRecurring Boolean có phải là nhiệm vụ lặp lại không.
     * @return JSONObject của nhiệm vụ đã thêm, hoặc null nếu có lỗi.
     */
    private JSONObject createTask(String title, String description, LocalDate dueDate,
                              String priorityLevel, boolean isRecurring) {

    JSONObject task = new JSONObject();
    task.put("id", UUID.randomUUID().toString()); // Có thể thay bằng ID đơn giản hơn
    task.put("title", title);
    task.put("description", description);
    task.put("due_date", dueDate.format(DATE_FORMATTER));
    task.put("priority", priorityLevel);
    task.put("status", "Chưa hoàn thành");
    task.put("created_at", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    task.put("last_updated_at", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    task.put("is_recurring", isRecurring); // Vi phạm YAGNI nếu chưa xử lý tính năng này
    if (isRecurring) {
        task.put("recurrence_pattern", "Chưa xác định");
    }
    return task;
}

        // Tải dữ liệu
        public class TaskDatabase {
    private static final String FILE_NAME = "tasks_database.json";

    public static JSONArray loadTasks() {
        // giống loadTasksFromDb()
    }

    public static void saveTasks(JSONArray tasks) {
        // giống saveTasksToDb()
    }
}


        System.out.println(String.format("Đã thêm nhiệm vụ mới thành công với ID: %s", taskId));
        return newTask;
    }

    public static void main(String[] args) {
        PersonalTaskManagerViolations manager = new PersonalTaskManagerViolations();
        System.out.println("\nThêm nhiệm vụ hợp lệ:");
        manager.addNewTaskWithViolations(
            "Mua sách",
            "Sách Công nghệ phần mềm.",
            "2025-07-20",
            "Cao",
            false
        );

        System.out.println("\nThêm nhiệm vụ trùng lặp (minh họa DRY - lặp lại code đọc/ghi DB và kiểm tra trùng):");
        manager.addNewTaskWithViolations(
            "Mua sách",
            "Sách Công nghệ phần mềm.",
            "2025-07-20",
            "Cao",
            false
        );

        System.out.println("\nThêm nhiệm vụ lặp lại (minh họa YAGNI - thêm tính năng không cần thiết ngay):");
        manager.addNewTaskWithViolations(
            "Tập thể dục",
            "Tập gym 1 tiếng.",
            "2025-07-21",
            "Trung bình",
            true 
        );

        System.out.println("\nThêm nhiệm vụ với tiêu đề rỗng:");
        manager.addNewTaskWithViolations(
            "",
            "Nhiệm vụ không có tiêu đề.",
            "2025-07-22",
            "Thấp",
            false
        );
    }
}
private String getCurrentTimestamp() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}