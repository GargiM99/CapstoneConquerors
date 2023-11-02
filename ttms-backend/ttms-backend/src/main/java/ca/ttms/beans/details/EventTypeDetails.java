package ca.ttms.beans.details;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventTypeDetails {
    private String eventName;
    private String eventDescription;
    private int dateDiff;
}
