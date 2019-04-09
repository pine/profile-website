package moe.pine.profile.services;

import moe.pine.profile.models.ViewMountain;
import moe.pine.profile.properties.MountainProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MountainService {
    @Nonnull
    private final MountainProperties mountainProperties;

    @Nonnull
    private final NumberFormat numberFormat;

    @Nonnull
    private final DateTimeFormatter dateTimeFormatter;

    public MountainService(
        final MountainProperties mountainProperties,
        @Qualifier("mountainNumberFormat") final NumberFormat numberFormat,
        @Qualifier("mountainDateTimeFormatter") final DateTimeFormatter dateTimeFormatter
    ) {
        this.mountainProperties = mountainProperties;
        this.numberFormat = numberFormat;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Nonnull
    public List<ViewMountain> getClimbed() {
        final var climbed = mountainProperties.getClimbed();
        return climbed.stream()
            .map(mountain -> {
                final String height = numberFormat.format(mountain.getHeight()) + "m";
                final String climbedAt =
                    mountain.getClimbedAt().atDay(1).format(dateTimeFormatter);
                return new ViewMountain(mountain.getName(), height, climbedAt);
            })
            .collect(Collectors.toUnmodifiableList());
    }
}
