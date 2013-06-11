from pygame import *

pictures  = []

for i in range(18):
    pic = image.load(""+str(i)+".png")
    pictures.append(pic)

running = True
screen = display.set_mode((1000,700))
frame = 0

while running:
    for evt in event.get():
        if evt.type == QUIT:
            running= False
    screen.fill((0,0,0))
    screen.blit(pictures[frame],(100,100))
    frame = (frame+1)%len(pictures)
    time.wait(50)
    display.flip()
quit()
