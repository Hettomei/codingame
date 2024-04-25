"""
cookie clicker
"""
# pylint: disable=global-statement

import time

import pyautogui

CURRENT_MODE = None

T_1_SECONDS = 1
T_5_SECONDS = 5
T_10_SECONDS = 10
T_20_MILLI_SECONDS = 0.02
T_500_MILLI_SECONDS = 0.5


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


def mode_nothing():
    """
    do nothing
    """


def mode_before_wait():
    """
    mode before wait
    """
    wait(T_5_SECONDS)
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
    wait(T_5_SECONDS)
    set_mode(mode_click)


def mode_click():
    """
    mode click
    """
    # time.sleep(T_20_MILLI_SECONDS)
    pyautogui.click()  # Click the mouse at its current location.

    mx, my = pos()
    if mx < 20 and my < 1000:
        set_mode(mode_before_wait)


def run():
    """
    run
    """
    set_mode(mode_before_click)
    while CURRENT_MODE:
        CURRENT_MODE()
    # pyautogui.moveTo(285, 471) # Move the mouse to the x, y coordinates 100, 150.
    print("done")


run()
