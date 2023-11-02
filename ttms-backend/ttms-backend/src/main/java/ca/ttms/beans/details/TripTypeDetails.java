package ca.ttms.beans.details;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripTypeDetails {
    private String typeName;
    private List<EventTypeDetails> eventTypes;
}