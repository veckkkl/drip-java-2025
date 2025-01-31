public class VideoLengthHomeWork {
    public static int minutesToSeconds(String videoLength) {
        String[] parts = videoLength.split(":");

        if (parts.length != 2) {
            return -1;
        }

        try {
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);

            if (seconds >= 60 || seconds < 0 || minutes < 0) {
                return -1;
            }
            return minutes * 60 + seconds;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
