
package com.thralloverhead;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum Thralls
{
    LESSER_GHOST(10878),
    SUPERIOR_GHOST(10879),
    GREATER_GHOST(10880),
    LESSER_GHOST_IMP(15701),
    SUPERIOR_GHOST_IMP(15702),
    GREATER_GHOST_IMP(15703),

    LESSER_SKELETON(10881),
    SUPERIOR_SKELETON(10882),
    GREATER_SKELETON(10883),
    LESSER_SKELETON_IMP(15704),
    SUPERIOR_SKELETON_IMP(15705),
    GREATER_SKELETON_IMP(15706),

    LESSER_ZOMBIE(10884),
    SUPERIOR_ZOMBIE(10885),
    GREATER_ZOMBIE(10886),
    LESSER_ZOMBIE_IMP(15707),
    SUPERIOR_ZOMBIE_IMP(15708),
    GREATER_ZOMBIE_IMP(15709);

    private final int id;

    Thralls(int id)
    {
        this.id = id;
    }

    public static final List<Integer> ALL_THRALLS =
        Arrays.stream(values())
            .map(Thralls::getId)
            .collect(Collectors.toList());

    public static final List<Integer> GHOSTS = List.of(
        LESSER_GHOST.id, SUPERIOR_GHOST.id, GREATER_GHOST.id,
        LESSER_GHOST_IMP.id, SUPERIOR_GHOST_IMP.id, GREATER_GHOST_IMP.id
    );

    public static final List<Integer> SKELETONS = List.of(
        LESSER_SKELETON.id, SUPERIOR_SKELETON.id, GREATER_SKELETON.id,
        LESSER_SKELETON_IMP.id, SUPERIOR_SKELETON_IMP.id, GREATER_SKELETON_IMP.id
    );

    public static final List<Integer> ZOMBIES = List.of(
        LESSER_ZOMBIE.id, SUPERIOR_ZOMBIE.id, GREATER_ZOMBIE.id,
        LESSER_ZOMBIE_IMP.id, SUPERIOR_ZOMBIE_IMP.id, GREATER_ZOMBIE_IMP.id
    );
}
