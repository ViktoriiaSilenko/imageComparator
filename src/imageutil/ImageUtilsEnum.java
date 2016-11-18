package imageutil;

public enum ImageUtilsEnum {

    IMAGE_RESULT_FILE_NAME("C:\\images\\image_result.png"),
    IMAGE1_PATH("C:\\images\\image1.png"),
    IMAGE2_PATH("C:\\images\\image2.png");

    ImageUtilsEnum(String text) {
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public static String getClassName() {
        return ImageUtilsEnum.class.getName();
    }
}
