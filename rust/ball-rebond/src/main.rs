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
}

impl Ball {
    fn new(x: f32, y: f32) -> Ball {
        Ball { x, y }
    }

    fn mmove(&mut self, dx: f32, dy: f32) {
        self.x += dx;
        self.y += dy;
    }

    fn display_position(&self) {
        println!("x:{:10.5}, y:{:10.5}", self.x, self.y)
    }
}

fn main() {
    let mut b = Ball::new(5.0, 10.0);

    println!("--- one ball ---");
    b.display_position();
    b.mmove(0.5, 0.005);
    println!("After move");
    b.display_position();

    println!("");
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
}
