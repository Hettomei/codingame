# pylint: disable=global-statement
"""
cookie clicker
"""

import argparse
import sys
import time

import pyautogui

CURRENT_MODE = None
START_TIME = time.time()
SECOND_BEFORE_CLICKING_EVERYWHERE = 25

TOTAL_CLICK_BEFORE_BUY = 1000

T_1_SECONDS = 1
T_3_SECONDS = 3
T_5_SECONDS = 5
T_10_SECONDS = 10
T_20_MILLI_SECONDS = 0.02
T_500_MILLI_SECONDS = 0.5

# pylint: disable=invalid-name
_args = None

# Creer des truc comme [cycle]
# avec du .next


def parse_args(sysargs):
    """
    parse args
    """
    parser = argparse.ArgumentParser(
        prog="Cookie Clicker Clicker",
        description="Click and buy",
        epilog="epilog.",
    )
    parser.add_argument("--test", action="store_true")
    parser.add_argument("--onlyclick", action="store_true")

    return parser.parse_args(sysargs)


def set_mode(mode):
    """
    set mode
    """
    global CURRENT_MODE
    print(f"change mode {mode}")
    CURRENT_MODE = mode


def wait(sec, dot=False):
    """
    do nothing
    """
    if dot:
        print(".", end="", flush=True)
    else:
        print(f"wait {sec}s")
    time.sleep(sec)


def pos(silent=True):
    """
    do nothing
    """
    x, y = pyautogui.position()
    if not silent:
        print(f"x: {x} - y: {y}")
    return x, y


def mode_before_wait():
    """
    mode before wait
    """
    wait(T_3_SECONDS)
    set_mode(mode_wait)


def mode_wait():
    """
    mode wait
    """
    wait(T_500_MILLI_SECONDS, dot=True)

    mx, my = pos()
    # en haut à gauche
    if mx <= 10 and my <= 10:
        # EXIT
        set_mode(None)
        return

    if mx < 10:
        set_mode(mode_before_click)


def mode_before_click():
    """
    mode before click
    """
    wait(T_3_SECONDS)
    set_mode(mode_click)


def mode_click():
    """
    mode click
    """
    global START_TIME
    # time.sleep(T_20_MILLI_SECONDS)
    pyautogui.click()  # Click the mouse at its current location.

    mx, _ = pos()
    if mx < 20:
        set_mode(mode_before_wait)
        return

    final_time = time.time() - START_TIME

    if final_time > SECOND_BEFORE_CLICKING_EVERYWHERE:
        START_TIME = time.time()
        set_mode(mode_click_partout)


def mode_click_partout():
    """
    buy click partout
    """
    global START_TIME
    if _args.onlyclick:
        START_TIME = time.time()
        set_mode(mode_click)
        return

    # wait_time = 0.1
    mx, my = pos()
    INITIALX = 20
    INITIALY = 170

    pyautogui.moveTo(INITIALX, INITIALY)
    # pyautogui.click()
    for __ in range(13):
        for _ in range(9):
            pyautogui.click()
            pyautogui.move(60, 0)
            newx, __ = pos()
            if newx < 20 :
                START_TIME = time.time()
                set_mode(mode_before_wait)
                return
        pyautogui.move(-9 * 60, 65)

    # reset
    pyautogui.moveTo(mx, my)
    START_TIME = time.time()
    set_mode(mode_click)


def mode_buy():
    """
    buy upgrade
    """
    if _args.onlyclick:
        set_mode(mode_click)
        return

    wait_time = 0.2
    mx, my = pos()

    pyautogui.moveTo(1800, 1000)
    pyautogui.click()
    for _ in range(9):
        wait(wait_time)
        pyautogui.move(0, -80)
        pyautogui.click()

    wait(wait_time)
    pyautogui.moveTo(1565, 140)
    pyautogui.click()

    # reset
    pyautogui.moveTo(mx, my)
    set_mode(mode_click)


def run(args):
    """
    run
    """
    print(args)
    set_mode(mode_before_click)
    while CURRENT_MODE:
        CURRENT_MODE()
    # pyautogui.moveTo(285, 471) # Move the mouse to the x, y coordinates 100, 150.
    print("done")


def runtest(args):
    """
    run test
    """
    print(args)
    pyautogui.moveTo(1800, 1000)  # Move the mouse to the x, y coordinates 100, 150.
    for _ in range(9):
        wait(0.2)
        pyautogui.move(0, -80)

    wait(1)
    pyautogui.moveTo(1565, 140)


if __name__ == "__main__":
    _args = parse_args(sys.argv[1:])
    if _args.test:
        runtest(_args)
    else:
        run(_args)
