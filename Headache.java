// Represents a single headache incident.

import java.time.Duration;
import java.time.LocalDateTime;

class Headache {
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private final int severity;
    
    public Headache(LocalDateTime startTime, int severity) {
        this.startTime = startTime;
        this.severity = severity;
    }
    
    public void endHeadache(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getSeverity() {
        return severity;
    }
    
    public long getDurationInMinutes() {
        if (endTime != null) {
            return Duration.between(startTime, endTime).toMinutes();
        }
        return -1;
    }
}

