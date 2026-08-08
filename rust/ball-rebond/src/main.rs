use macroquad::prelude::*;

const GRAVITY: f32 = 500.0;

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
}

impl Ball {
    fn new(x: f32, y: f32) -> Ball {
        Ball {
            x,
            y,
            vx: 0.0,
            vy: 0.0,
        }
    }

    fn mmove(&mut self, dx: f32, dy: f32) {
        self.x += dx;
        self.y += dy;
    }

    fn set_vx(&mut self, vx: f32) {
        self.vx = vx;
    }

    fn invert_vy(&mut self) {
        self.vy *= -1.0;
    }

    fn update(&mut self, dt: f32) {
        self.vy += GRAVITY * dt;

        self.x += self.vx * dt;
        self.y += self.vy * dt;
    }

    fn display_position(&self) {
        println!("x:{:10.5}, y:{:10.5}", self.x, self.y)
    }
}

#[macroquad::main("Mon premier programme graphique")]
async fn main() {
    let mut ball = Ball::new(50.0, 10.0);
    ball.set_vx(100.0);

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
        if ball.y > screen_height() - 30.0 {
            ball.invert_vy();
        }
        if ball.x > screen_width() - 30.0 {
            ball.set_vx(-100.0);
        }
        if ball.x < 30.0 {
            ball.set_vx(100.0);
        }
        let dt = get_frame_time();
        ball.update(dt);

        clear_background(BEIGE);
        draw_rectangle(0.0, 550.0, screen_width(), 50.0, BROWN);
        draw_circle(ball.x, ball.y, 30.0, ORANGE);

        next_frame().await;
    }
}
