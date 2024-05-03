# pylint: disable=global-statement
"""
cookie clicker
"""

import argparse
import sys
import time

import pyautogui
from pyautogui import ImageNotFoundException

CURRENT_MODE = None
START_TIME = time.time()
SECONDS_BEFORE_FIND_GOLD = 120

T_2_SECONDS = 2
T_500_MILLI_SECONDS = 0.5

# pylint: disable=invalid-name
_args = None


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
    print(f"->  {mode.__name__}")
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
    wait(T_2_SECONDS)
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
    wait(T_2_SECONDS)
    set_mode(mode_click)


def mode_click():
    """
    mode click
    """
    global START_TIME
    pyautogui.click()  # Click the mouse at its current location.

    mx, _ = pos()
    if mx < 20:
        set_mode(mode_before_wait)
        return

    final_time = time.time() - START_TIME

    if final_time > SECONDS_BEFORE_FIND_GOLD:
        START_TIME = time.time()
        set_mode(mode_click_golden)


def mode_click_golden():
    """
    click golden
    """
    global START_TIME
    if _args.onlyclick:
        START_TIME = time.time()
        set_mode(mode_click)
        return

    mx, my = pos()

    for i in range(1, 10):
        try:
            a = pyautogui.locateOnScreen("img/centre.png", region=(0, 0, 610, 900))
            print(i, a)
            pyautogui.click(a)
            break
        except ImageNotFoundException:
            pass

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
    print("done")


def runtest(args):
    """
    run test
    """
    print(args)
    pyautogui.moveTo(1800, 1000)
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
