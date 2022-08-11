import sys
import sdl2
import sdl2.ext

WHITE = sdl2.ext.Color(255, 255, 255)


class SoftwareRenderer(sdl2.ext.SoftwareSpriteRenderSystem):
    def __init__(self, window):
        super(SoftwareRenderer, self).__init__(window)

    def render(self, components):
        sdl2.ext.fill(self.surface, sdl2.ext.Color(0, 0, 0))
        super(SoftwareRenderer, self).render(components)


class Player(sdl2.ext.Entity):
    def __init__(self, world, sprite, posx=0, posy=0):
        self.sprite = sprite
        self.sprite.position = posx, posy


def run():
    sdl2.ext.init()
    window = sdl2.ext.Window("The Pong Game", size=(1100, 900))
    window.show()

    world = sdl2.ext.World()

    spriterenderer = SoftwareRenderer(window)
    world.add_system(spriterenderer)

    factory = sdl2.ext.SpriteFactory(sdl2.ext.SOFTWARE)
    sp_paddle1 = factory.from_color(WHITE, size=(20, 100))
    sp_paddle2 = factory.from_color(WHITE, size=(20, 100))

    player1 = Player(world, sp_paddle1, 0, 250)
    player2 = Player(world, sp_paddle2, 780, 250)

    for i in range(10, 1100, 20):
        for j in range(10, 900, 20):
            a = factory.from_color(WHITE, size=(1, 1))
            player2 = Player(world, a, i, j)

    running = True
    while running:
        for event in sdl2.ext.get_events():
            # print(event, event.type)
            if event.type == sdl2.SDL_QUIT:
                running = False
                break
            if event.type == sdl2.SDL_KEYDOWN:
                if event.key.keysym.sym in (sdl2.SDLK_ESCAPE, sdl2.SDLK_q) :
                    running = False
                    break
            if event.type == sdl2.SDL_MOUSEMOTION:
                motion = event.motion
                print(motion.x, motion.xrel, motion.y, motion.yrel)
                a = factory.from_color(WHITE, size=(abs(motion.xrel), abs(motion.yrel)))
                player2 = Player(world, a, motion.x, motion.y)

        world.process()
    return 0


if __name__ == "__main__":
    sys.exit(run())
