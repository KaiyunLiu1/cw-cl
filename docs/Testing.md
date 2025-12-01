## 1. Regression Test Plan (v1 Behaviour)

This section documents the high-level regression tests created for the original Swing (v1) version of the game.  
The goal of these tests is to capture *observable behaviour* (movement, physics, combat, enemy AI, map, camera, win condition) so that, after refactoring the codebase to an MVC + JavaFX architecture, we can re-run the same tests on v2 and demonstrate that the behaviour of v1 and v2 is identical.

Each test case below is implemented in `RegressionTest.java` and is cross-referenced with a Git commit where it was introduced or last updated.

| ID  | Test method name                                  | Purpose                                                                                                                                                                                          | Main classes & features covered                                           | Git Commit |
|-----|---------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|-----------|
| R1  | `playerMovesRightWhenDHeld`                       | Verify that pressing the D key causes the player’s x-position to increase over multiple `update()` calls.                                                                                        | `Player.update`, `canvas.keysPressed`, horizontal movement                | `a1d67fe` |
| R2  | `playerMovesLeftWhenAHeld`                        | Verify that pressing the A key causes the player’s x-position to decrease over multiple `update()` calls.                                                                                        | `Player.update`, `canvas.keysPressed`, horizontal movement                | `a1d67fe` |
| R3  | `playerJumpsUpwardsWhenWPressed`                  | Ensure that when the player jumps from the ground, the y-position decreases at least once (moving upwards) before increasing again due to gravity (falling).                                    | `Player.jump`, `Player.update`, gravity and jump logic                    | `a1d67fe` |
| R4  | `playerFallsWhenNoGround`                         | Ensure that when there is no ground below the player, gravity causes the y-position to increase over time (falling downwards).                                                                  | `Player.update`, `entity.gravity`, world without ground                   | `a1d67fe` |
| R5  | `playerStopsOnGround`                             | Ensure that when there is a block beneath the player, they eventually land and become grounded (`isGrounded = true`).                                                                            | `Player.update`, `entity.intersect`, `MapBlocks.map`                      | `a1d67fe` |
| R6  | `playerFacingDirectionUsesLastDirectionWhenIdle`  | Check that when no movement keys are pressed, the player’s facing direction falls back to the last recorded movement direction.                                                                  | `Player.isFacingForwards`, `canvas.isLastDirectionForwards`, input state | `a1d67fe` |
| R7  | `shootingCreatesBulletAndConsumesAmmo`            | Verify that pressing shoot decreases ammo by one and spawns exactly one bullet in the active bullets list.                                                                                       | `Player.shoot`, `Player.update`, `canvas.activeBullets`, ammo tracking    | `a1d67fe` |
| R8  | `playerCannotShootDuringCooldown`                 | Ensure that attempting to shoot again during the cooldown period does not create extra bullets.                                                                                                  | `Player.shoot`, shoot cooldown (`justShot`, `lastShot`)                   | `a1d67fe` |
| R9  | `damageAppliesHealthLossKnockbackAndCooldown`     | Check that when the player takes damage from an enemy, health decreases, knockback and upward velocity are applied, and damage cooldown prevents repeated damage.                                | `Player.damage`, `Enemy`, damage flags, knockback and vertical velocity   | `a1d67fe` |
| R10 | `playerDeathRespawnsAndIncrementsCounter`         | Verify that killing the player increments the global death counter and respawns a new `Player` instance.                                                                                         | `Player.kill`, `Player.deathCounter`, `Game.spawnEntities`, `canvas.player` | `a1d67fe` |
| R11 | `bulletRemovedAfterMaxDistance`                   | Ensure that bullets are removed from the active list after travelling their maximum allowed distance.                                                                                            | `bullet.update`, `canvas.activeBullets`, bullet travel distance           | `a1d67fe` |
| R12 | `bulletDamagesEnemy`                              | Verify that when a bullet collides with an enemy, the enemy’s health is reduced.                                                                                                                 | `bullet.update`, bullet–enemy collision, `Enemy.health`, `canvas.enemies` | `a1d67fe` |
| R13 | `enemyStopsWhenPlayerOutsideDetectionRange`       | Ensure that an enemy does not move when the player is outside its detection radius.                                                                                                              | `Enemy.doBehavior`, detection range check, idle behaviour                 | `a1d67fe` |
| R14 | `enemyAdjustsMovementAndFacingRelativeToPlayer`   | Ensure that when the enemy is within detection range, it (1) moves left towards a player on its left, (2) moves right towards a player on its right, and (3) stops when very close to the player. | `Enemy.doBehavior`, horizontal speed adjustment, `isFacingForwards`, close-range stop (`Math.abs(x - p.x) < 20`) | `a1d67fe` |
| R15 | `enemyDamageAndDeathRemovesFromList`              | Verify that an enemy becomes damaged after being hit and, when health reaches 0, is removed from the global enemy list.                                                                          | `Enemy.damage`, `Enemy.doBehavior`, death condition, `canvas.enemies`     | `a1d67fe` |
| R16 | `enemyAnimationStateTransitionsCorrectly`         | Check that the enemy’s state machine transitions between `walking`, `running` and `aerial` based on movement, grounded and running flags.                                                        | `Enemy.updateState`, `entitystate`, `isGrounded`, `isRunning`, `speed`    | `a1d67fe` |
| R17 | `reachingTunnelTriggersWin`                       | Ensure that when the player’s x-position passes the tunnel threshold, the game win flag is set to true.                                                                                          | `Player.update`, `CyborgPlatform.game.isWon`, level end condition         | `a1d67fe` |
| R18 | `mapBlocksLoadsCorrectTileLayout`                 | Verify that the map parsing logic correctly creates blocks for tiles except those marked with `'0'`.                                                                                             | `MapBlocks`, `MapBlocks.map`, `MapBlocks.mapWidth`, tile parsing          | `a1d67fe` |
| R19 | `cameraFollowsPlayerCorrectly`                    | Ensure that the camera offset follows the player in the middle of the map and clamps at the left and right boundaries.                                                                           | `Player.updateCmr`, `MapBlocks.mapWidth`, camera behaviour                | `a1d67fe` |

---

## 2. Initial Tests for the Redesign (v2)

As required by Task 3, initial tests for the planned redesign are created. These tests are organised around the three main layers of the future architecture:

- **Model layer** – the characters' game state and action logic. Most class names will be the same as version 1.
- **Controller layer** – responsible for translating input events into model commands, for example, key presses → move / jump / shoot. Class names will contain the word `Controller`.
- **View layer** – responsible for rendering player state and GUI elements such as health hearts, ammo, timer, animations, camera. Class names will contain the word `View`.

At this stage the full refactor has not yet been implemented, so these tests are left as disabled stub tests (where appropriate) to capture the intended behaviour for v2.

### 2.1 Tests for Model Layer

These tests are written to capture model-level behaviour.

#### `entityStubTest` -> `entity`

| ID | Test method name                               | Purpose / Behaviour under test                                                                                                                                                                                                                              | Git Commit |
|----|------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S1 | `intersectWhenNoBlocks`                        | Verifies that when there are no map blocks in `MapBlocks.map`, the refactored `intersect()` method returns `false`. This establishes the baseline that an entity with no overlapping tiles must not be reported as colliding.                              |           |
| S2 | `intersectWhenOverlappingBlock`                | Verifies that when the entity’s bounding rectangle overlaps a tile, `entity.intersect()` returns `true`. This confirms that the collision check detects an overlap between the entity and a single map block.                                              |           |
| S3 | `gravitySetsEntityGroundedWhenCollidingWithBlock` | Verifies that `entity.gravity()` sets `isGrounded = true` when there is a tile directly below the entity and the vertical projection collides with that tile. This uses the real `MapBlocks.map` to simulate a ground tile at `y + 1` with downward velocity. |           |
| S4 | `applyFreeFallUpdatesPositionAndVelocity`      | Verifies that `applyFreeFall()` moves the entity downwards by its current `velocity`, increases `velocity` by `acceleration`, and clears the grounded flag. After the call, `y` should be updated, `velocity` incremented, and `isGrounded` set to `false`. |           |
| S5 | `handleVerticalCollisionDampensLargeDownwardVelocity` | Verifies that `handleVerticalCollision()` reduces a large downward `velocity` (e.g. 3.0) to a smaller value (e.g. 2.0), modelling landing damping, while keeping `isGrounded` as `false`. This captures the behaviour of softening fast downward impacts.     |           |
| S6 | `handleVerticalCollisionBouncesUpwardVelocity` | Verifies that when `velocity` is upward/negative (e.g. -4.0), `handleVerticalCollision()` converts it into a reduced downward “bounce” velocity (e.g. 1.0) and keeps `isGrounded` as `false`. This models bouncing off a ceiling while remaining airborne.    |           |

#### `PlayerStubTest` -> `Player`

| ID  | Test method name                        | Purpose / Behaviour under test                                                                                                                                                                  | Git Commit |
|-----|----------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S7  | `movement`                             | Planned stub test: will verify that horizontal speed and x-position update correctly based on left/right inputs and collisions in a future `Player.movement(moveLeft, moveRight)` method.       |           |
| S8  | `jumpState`                            | Planned stub test: will check that `jumpCounter` and vertical `acceleration` are updated correctly based on grounded state and jump input in a future `Player.jumpState(jumpPressed)` method.   |           |
| S9  | `deathCondition`                       | Planned stub test: will verify that a `deadOrFallen` flag is set when the player falls below the map (y > threshold) or when `health <= 0`.                                                     |           |
| S10 | `winCondition_plannedBehaviour`        | Planned stub test: will verify that a “reached goal” flag is set when the player’s x-position passes the level end position (e.g. x > 8000).                                                   |           |
| S11 | `playerDamagedCooldown_plannedBehaviour` | Planned stub test: will check that `isDamaged` is reset to `false` once the damage cooldown time has fully elapsed in a dedicated damage cooldown helper.                                       |           |
| S12 | `playerShootCooldown_plannedBehaviour` | Planned stub test: will verify that `justShot` becomes `false` after the shooting cooldown expires so the player can fire again, handled by a dedicated shooting cooldown helper.               |           |

#### `EnemyStubTest.java` -> `Enemy`

| ID  | Test method name           | Purpose / Behaviour under test                                                                                                                                                              | Git Commit |
|-----|----------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S13 | `distanceFromPlayer`       | Verifies that the Euclidean distance between the enemy and the player is correctly computed (e.g. from (100,100) to (103,104) should be distance 5.0) in a future `Enemy.distanceFromPlayer(Player)` helper.                    |           |
| S14 | `isMoving`                 | Verifies that `Enemy.isMoving()` returns `false` when `speed == 0` and `true` when `speed != 0`, defining a clear predicate for whether the enemy is currently in motion.                                                        |           |
| S15 | `isDead`                   | Planned stub test: will verify that `Enemy.isDead()` returns `true` when `health <= 0` or when the enemy has fallen below the death Y threshold (y > 900), otherwise `false`.                                                    |           |
| S16 | `applyDamageSlowdownHalves` | Planned stub test: will ensure that `applyDamageSlowdown()` halves the enemy’s `speed` when `isDamaged == true`, capturing “damaged slowdown” behaviour as a separate, testable unit.                                           |           |
| S17 | `applyDamageSlowdownUnchanged` | Planned stub test: will ensure that `applyDamageSlowdown()` leaves `speed` unchanged when `isDamaged == false`, so that slowdown only applies while damaged.                                                                    |           |
| S18 | `updateDamageCooldown`     | Planned stub test: will verify the behaviour of `updateDamageCooldown(long now)`: when the elapsed time exceeds the cooldown threshold, `isDamaged` is reset to `false`; otherwise it remains `true`.                           |           |

#### `BulletStubTest.java` -> `bullet`

| ID  | Test method name             | Purpose / Behaviour under test                                                                                                                                                                   | Git Commit |
|-----|------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S19 | `moveForward_stub`           | Planned stub test: will verify that when nothing blocks the bullet, its `x` coordinate increases by the current `speed`. This extracts “horizontal movement” from `update()` into `moveForward()`. |           |
| S20 | `removeBullet_stub`          | Planned stub test: will define and verify the behaviour of removing the current bullet instance from the active bullets collection managed by the game.                                         |           |
| S21 | `NoCollisionAndWithinRange`  | Planned stub test: will verify that `bullet.shouldBeRemoved()` returns `false` when the bullet is safe (no collisions) and still within its maximum travel distance.                           |           |
| S22 | `achieveMaxDistance`         | Planned stub test: will verify that `bullet.shouldBeRemoved()` returns `true` when the bullet has reached or exceeded its maximum travel distance, even without collisions.                    |           |

#### `TileMapLoaderTest`

It's extracted from `MapBlocks` class, responsible for tile map loading.

| ID  | Test method name | Purpose / Behaviour under test                                                                                                                                          | Git Commit |
|-----|------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S23 | `load_stub`      | Planned stub test: will ensure that `TileMapLoader.load(String path)` can be invoked (currently with a dummy path) and reserves a place for future real map-loading behaviour tests. |           |

---

### 2.2 Tests for Controller Layer

These tests are currently stubs with TODO comments. They will be implemented in Task 4 when a dedicated controller is completed.  
Due to stub methods, it is difficult to add all controller-layer tests in this task. More controller-layer tests will be added in Task 4.

#### `PlayerControllerTest.java` -> `PlayerController`

| ID  | Test method name    | Purpose / Behaviour under test                                                                                                                                      | Git Commit |
|-----|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S24 | `pressingRightKey`  | Planned stub test: will simulate a “right key pressed” event on the controller. This should result in a model call that moves the player to the right, replacing direct use of `canvas.keysPressed[1]`. |           |
| S25 | `pressingJumpKey`   | Planned stub test: will simulate a “jump key pressed” event and verify that the controller triggers a jump on the model (updating vertical velocity and airborne state).                               |           |
| S26 | `pressingShootKey`  | Planned stub test: will simulate a “shoot key pressed” event and verify that the controller invokes the model’s shoot behaviour (ammo decrease + bullet creation).                                      |           |

---

### 2.3 Tests for View Layer

These tests are currently annotated with `@Disabled`. They capture how the JavaFX view should respond to model changes.

#### `PlayerViewTest.java` -> `PlayerView`

| ID  | Test method name                                   | Purpose / Behaviour under test                                                                                                                                                             | Git Commit |
|-----|----------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S27 | `viewUpdatesHeartsWhenHealthChanges`               | Stub test: the number of rendered heart icons in the JavaFX view should update when the model’s health changes (replacing the heart drawing logic in the Swing `drawGUI`).                                                       |           |
| S28 | `viewReflectsPlayerStateWithCorrectSpriteOrAnimation` | Stub test: when the model state changes between idle, running, aerial, hurt, and shooting, the JavaFX view should switch to the corresponding sprite or animation images.                                                       |           |
| S29 | `cameraFollowsPlayerAndClampsToWorldBounds`        | Stub test: camera / root-node translation in the JavaFX view should follow the player while clamping to the map edges, mirroring the behaviour currently provided by `Player.updateCmr()`.                                      |           |

#### `EnemyViewTest.java` -> `EnemyView`

| ID  | Test method name                            | Purpose / Behaviour under test                                                                                                                                                          | Git Commit |
|-----|---------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S30 | `enemyViewShowsCorrectAnimationForGroundStates` | Stub test: when the `Enemy` model state is `idle`, `walking` or `running`, the `EnemyView` should select the corresponding ground animation sequence.                                   |           |
| S31 | `enemyViewShowsAerialSpriteWhenEnemyIsInAir`    | Stub test: when the `Enemy` model is in the `aerial` state with a negative vertical velocity, the view should display the appropriate aerial sprite or animation.                       |           |
| S32 | `enemyViewShowsHurtSpriteWhenEnemyIsDamaged`    | Stub test: when the `Enemy` model enters the `hurt` state, the view should display the dedicated hurt animation, replacing the `"hurt"` case in the original `animate()` implementation. |           |

#### `GameViewTest` -> `GameView`

| ID  | Test method name           | Purpose / Behaviour under test                                                                                                                                                             | Git Commit |
|-----|----------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S33 | `drawHeartsBasedOnHealth`  | Planned stub test: will verify that heart icons can be drawn correctly based on the player’s health, with the correct number of hearts rendered at fixed HUD positions.                                                        |           |
| S34 | `drawAmmoBoxAndCount`      | Planned stub test: will verify that the ammo box icon is drawn at a fixed position and that the numeric ammo count text matches `player.ammo`.                                                                                |           |
| S35 | `drawGameTimer`            | Planned stub test: will check that the elapsed game time is formatted as `"mm:ss"` and drawn at the intended HUD location in the JavaFX `GameView`.                                                                            |           |
| S36 | `drawControlHints`         | Planned stub test: will verify that static control hint texts are rendered correctly at fixed on-screen positions, independent of the player’s in-world position.                                                              |           |
| S37 | `drawWinnerTunnelHints`    | Planned stub test: will verify that the winner-tunnel hints are rendered when the camera / viewport is near the tunnel area (e.g. texts like `"This is The Winner Tunnel"`, `"Just Keep Walking!"`).                           |           |
| S38 | `drawAttemptsCounter`      | Planned stub test: will verify that the number of attempts is drawn at a fixed HUD position with the correct value `ATTEMPTS: X` where `X == Player.deathCounter`.                                                            |           |

#### `TileMapRenderTest`

It's extracted from `MapBlocks` class, responsible for tile map rendering.

| ID  | Test method name | Purpose / Behaviour under test                                                                                                                                                             | Git Commit |
|-----|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S39 | `render_stub`    | Planned stub test: will ensure that `TileMapRenderer` can be constructed with a tile-image map and that `render(GraphicsContext, TileMap, double)` can be invoked without errors (placeholder only for future rendering behaviour). |           |

#### `BackgroundRenderTest` -> `BackgroundRenderer`

It's extracted from `Background` class, responsible for parallax background rendering.

| ID  | Test method name | Purpose / Behaviour under test                                                                                                                                                                                                                      | Git Commit |
|-----|------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S40 | `render_stub`    | Stub test: verifies the planned behaviour of the future `BackgroundRenderer.render(...)` method. It will construct the renderer with an array of background layers and check that it can render a parallax background correctly.                     |           |

---

### 2.4 Tests for utils layer

#### `CollisionUtilsTest` -> `CollisionUtils`

| ID  | Test method name   | Purpose / Behaviour under test                                                                                                                                                          | Git Commit |
|-----|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| S41 | `overlaps_stub`    | Planned stub test: will verify that the shared rectangle-overlap utility correctly reports collisions between AABBs for tiles, enemies, and bullets. This utility will be invoked across multiple classes.                    |           |
