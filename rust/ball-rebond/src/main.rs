use macroquad::prelude::*;

const GRAVITY: f32 = 800.0;

struct World {
    balls: Vec<Ball>,
}

impl World {
    fn new() -> World {
        let mut balls = Vec::new();
        balls.push(Ball::new(10.0, 20.0));
        balls.push(Ball::new(50.0, 100.0));
        balls.push(Ball::new(200.0, 300.0));

        World { balls }
    }
    fn move_balls(&mut self, dx: f32, dy: f32) {
        for ball in &mut self.balls {
            ball.mmove(dx, dy);
        }
    }
}

struct Ball {
    x: f32,
    y: f32,
    vx: f32,
    vy: f32,
    incx: f32,
    color: Color,
}

impl Ball {
    fn new(x: f32, y: f32) -> Ball {
        Ball {
            x,
            y,
            vx: 0.0,
            vy: 0.0,
            incx: 1.0,
            color: Color::from_rgba(
                rand::gen_range(0, 255),
                rand::gen_range(0, 255),
                rand::gen_range(0, 255),
                rand::gen_range(200, 255),
            ),
        }
    }

    fn mmove(&mut self, dx: f32, dy: f32) {
        self.x += dx;
        self.y += dy;
    }

    fn random_color(&mut self) {
        self.color.r = rand::gen_range(0.0, 1.0);
        self.color.g = rand::gen_range(0.0, 1.0);
        self.color.b = rand::gen_range(0.0, 1.0);
        self.color.a = rand::gen_range(0.8, 1.0);
    }

    fn set_vx(&mut self, vx: f32) {
        self.vx = vx;
    }

    fn set_y(&mut self, y: f32) {
        self.y = y;
    }

    fn set_x(&mut self, x: f32) {
        self.x = x;
    }

    fn invert_vx(&mut self) {
        self.vx *= -1.0;
    }

    fn invert_vy(&mut self) {
        self.vy *= -1.0;
    }

    fn update(&mut self, dt: f32) {
        // self.incx += 1.0;

        self.vy += GRAVITY * dt;

        self.x += (self.vx + self.incx) * dt;
        self.y += self.vy * dt;
    }

    fn display_position(&self) {
        println!("x:{:10.5}, y:{:10.5}", self.x, self.y)
    }
}

#[macroquad::main("Ball")]
async fn main() {
    let mut ball = Ball::new(50.0, 100.0);
    ball.set_vx(200.0);

    println!("--- one world ---");
    let mut world = World::new();
    for b in &world.balls {
        b.display_position();
    }

    println!("After move");
    world.move_balls(5.0, 6.0);

    for b in &world.balls {
        b.display_position();
    }

    loop {
        if ball.y > screen_height() - 60.0 {
            ball.invert_vy();
            // avoid a bug when it stay at exact position and "float" at bottom
            ball.set_y(screen_height() - 60.0 - 1.0);
            ball.random_color();
        }
        if ball.x > screen_width() - 30.0 {
            ball.invert_vx();
            ball.set_x(screen_width() - 30.0 - 5.0);
            ball.random_color();
        }
        if ball.x < 30.0 {
            ball.invert_vx();
            ball.set_x(30.0 + 0.1);
            ball.random_color();
        }
        let dt = get_frame_time();
        ball.update(dt);

        // if get_fps() > 490 {
        //     println!("{:10}", get_fps());
        // }

        clear_background(BEIGE);
        draw_rectangle(0.0, 550.0, screen_width(), 50.0, BROWN);
        draw_circle(ball.x, ball.y, 30.0, ball.color);

        next_frame().await;
    }
}
