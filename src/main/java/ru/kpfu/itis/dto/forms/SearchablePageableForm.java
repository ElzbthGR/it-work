package ru.kpfu.itis.dto.forms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.kpfu.itis.dto.PhoneNumber;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchablePageableForm extends PageableForm {

    private String query;

    @Nullable
    @JsonIgnore
    public String getFormattedQuery() {
        return formatQuery(query);
    }

    @Nullable
    @JsonIgnore
    public String getFormattedQueryForPhone() {
        return formatQueryForPhone(query);
    }

    @Nullable
    @JsonIgnore
    protected String formatQueryForPhone(String query) {
        String queryForPhone = PhoneNumber.cleanUp(query);
        return formatQuery(queryForPhone);
    }

    @Nullable
    @JsonIgnore
    private static String formatQuery(String query) {
        if (query == null) {
            return null;
        }
        String formattedQuery = query.toLowerCase().trim();
        formattedQuery = StringUtils.appendIfMissing(formattedQuery, "%");
        formattedQuery = StringUtils.prependIfMissing(formattedQuery, "%");
        return formattedQuery;
    }
}
