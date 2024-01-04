package me.syncwrld.boosters;

import java.util.Collections;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import me.syncwrld.boosters.core.BaseBooster;

@Accessors(fluent = true, chain = true) @Data
public class BoosterLoader {
  private final Set<BaseBooster> boosters = Collections.emptySet();
}
