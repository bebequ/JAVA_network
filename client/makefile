CC=gcc
CFLAGS=-g -O0
LFLAGS=

TARGET=ClientProgram

SRC=clientprogram.c
OBJ_DIR=Debug
OBJ=$(SRC:%.c=$(OBJ_DIR)/%.o)

all:$(TARGET)

$(TARGET):$(OBJ)
	$(CC) -o $@ $^ $(LFLAGS)

$(OBJ):$(SRC)
	@[ -d $(OBJ_DIR) ] || mkdir $(OBJ_DIR)
	$(CC) -c $^ -o $@ $(CFLAGS)

clean :
	rm -rf $(OBJ) $(TARGET)
	rmdir $(OBJ_DIR)
