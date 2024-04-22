package vn.vnpt.common.constant;

public final class DatabaseStatus {
    public static final String Success = "success";
    public static final String IsExist = "is_exist";
    public static final String NotFound = "not_found";

    /**
     * Hàm kiểm tra kết quả out_result lỗi tồn tại không <br>
     * Function example: common_c_error_is_exist('name'); <br>
     * return prs_object || ' is_exist'; <br>
     * Đối với biến prs_object là một mảng trả về các trường tồn tại
     * trong json message với dạng chuỗi ký tự ngăn cách bằng ',' <br>
     * Ví dụ: 'name,storage_credential_id,access_key' <br>
     *
     * @param result kết quả trả về từ database
     * @return true có key 'is_exist' trong kết quả trả về
     */
    public static boolean isExist(String result) {
        return result.contains(IsExist);
    }

    /**
     * Hàm kiểm tra kết quả out_result lỗi not_found không <br>
     * Function example: common_c_error_not_found('storage_credential_id: ' || prs_storage_credential_id); <br>
     * return prs_object || ' not_found'; <br>
     *
     * @param result kết quả trả về từ database
     * @return true có key 'not_found' trong kết quả trả về
     */
    public static boolean notFound(String result) {
        return result.contains(NotFound);
    }
}