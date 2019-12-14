package com.builderboy.bim.common.block.machine;

import com.builderboy.bim.util.ShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ConveyorBlock extends MachineBlock {

    private static final EnumProperty<ConveyorShape> SHAPE = EnumProperty.create("shape", ConveyorShape.class);
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape[] SHAPES = createShapes();

    public ConveyorBlock() {
        super(3.5f, 3.0f);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH).with(SHAPE, ConveyorShape.STRAIGHT));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return getConveyorVoxel(state, world, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(SHAPE, ConveyorShape.STRAIGHT);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!isMoving) {

            Direction face = state.get(FACING);

            BlockState fromState = world.getBlockState(fromPos);
            if (state.get(FACING) == fromState.get(FACING)) { world.setBlockState(fromPos, fromState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT)); }

            int difX = pos.getX() - fromPos.getX();
            int difZ = pos.getZ() - fromPos.getZ();

            System.out.println("X: "+difX);
            System.out.println("Z: "+difZ);

            switch (face) {
                case NORTH:
                    if (difZ == -1) { world.setBlockState(fromPos, state.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT)); }
                    if (difZ == 1) { world.setBlockState(fromPos, state.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT)); }
                break;

                case SOUTH:
                    if (difZ == 1) { world.setBlockState(fromPos, state.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT)); }
                    if (difZ == -1) { world.setBlockState(fromPos, state.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT)); }
                break;

                case EAST:
                    if (difX == -1) { world.setBlockState(fromPos, state.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT)); }
                    if (difX == 1) { world.setBlockState(fromPos, state.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT)); }
                break;

                case WEST:
                    if (difX == 1) { world.setBlockState(fromPos, state.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT)); }
                    if (difX == -1) { world.setBlockState(fromPos, state.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT)); }
                break;
            }

            this.setDefaultState(state.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE);
    }

    private VoxelShape getConveyorVoxel(BlockState state, IBlockReader world, BlockPos pos) {
        BlockPos negX = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
        BlockPos posX = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
        BlockPos negZ = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
        BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);

        Block negXBlock = world.getBlockState(negX).getBlock();
        Block posXBlock = world.getBlockState(posX).getBlock();
        Block negZBlock = world.getBlockState(negZ).getBlock();
        Block posZBlock = world.getBlockState(posZ).getBlock();

        switch (state.get(FACING)) {
            case NORTH:
                if () {}
                //if (negXBlock instanceof ConveyorBlock) { return SHAPES[getShapeIndex(Direction.NORTH, ConveyorShape.TURN_RIGHT)]; }
                //if (world.getBlockState(posX).getBlock() instanceof ConveyorBlock) { return SHAPES[getShapeIndex(Direction.NORTH, ConveyorShape.TURN_LEFT)]; }

                break;
            case SOUTH:
                if (world.getBlockState(posX).getBlock() instanceof ConveyorBlock) { return SHAPES[getShapeIndex(Direction.SOUTH, ConveyorShape.TURN_RIGHT)]; }
                if (world.getBlockState(negX).getBlock() instanceof ConveyorBlock) { return SHAPES[getShapeIndex(Direction.SOUTH, ConveyorShape.TURN_LEFT)]; }

                break;
            case EAST:
                if (world.getBlockState(negZ).getBlock() instanceof ConveyorBlock) { return SHAPES[getShapeIndex(Direction.EAST, ConveyorShape.TURN_RIGHT)]; }
                if (world.getBlockState(posZ).getBlock() instanceof ConveyorBlock) { return SHAPES[getShapeIndex(Direction.EAST, ConveyorShape.TURN_LEFT)]; }

                break;
            case WEST:
                if (world.getBlockState(posZ).getBlock() instanceof ConveyorBlock) { return SHAPES[getShapeIndex(Direction.WEST, ConveyorShape.TURN_RIGHT)]; }
                if (world.getBlockState(negZ).getBlock() instanceof ConveyorBlock) { return SHAPES[getShapeIndex(Direction.WEST, ConveyorShape.TURN_LEFT)]; }

                break;
        }

        return SHAPES[state.get(FACING).getHorizontalIndex()];
    }

    private int getShapeIndex(Direction face, ConveyorShape shape) {
        switch (shape) {
            case STRAIGHT:
                return face.getHorizontalIndex();
            case TURN_LEFT:
                return 4 + face.getHorizontalIndex();
            case TURN_RIGHT:
                return 8 + face.getHorizontalIndex();
        }

        return 0;
    }

    private static VoxelShape[] createShapes() {
        VoxelShape[] shapes = new VoxelShape[12];

        for (int i = 0; i < shapes.length; i++) {

            int dirIndex = i % 4;
            int shapeIndex = i / 4;
            Direction direction = Direction.byHorizontalIndex(dirIndex);
            ConveyorShape shape = ConveyorShape.getFromIndex(shapeIndex);

            VoxelShape newShape;

            switch (shape) {
                case STRAIGHT:
                    newShape = VoxelShapes.combineAndSimplify(ShapeHelper.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D, direction), ShapeHelper.makeCuboidShape(0.0D, 3.0D, 0.0D, 2.0D, 5.0D, 16.0D, direction), IBooleanFunction.OR);
                    newShape = VoxelShapes.combineAndSimplify(newShape, ShapeHelper.makeCuboidShape(14.0D, 3.0D, 0.0D, 16.0D, 5.0D, 16D, direction), IBooleanFunction.OR);
                    shapes[i] = newShape;
                    break;
                case TURN_LEFT:
                    newShape = VoxelShapes.combineAndSimplify(ShapeHelper.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D, direction), ShapeHelper.makeCuboidShape(0.0D, 3.0D, 0.0D, 2.0D, 5.0D, 16.0D, direction), IBooleanFunction.OR);
                    newShape = VoxelShapes.combineAndSimplify(newShape, ShapeHelper.makeCuboidShape(14.0D, 3.0D, 0.0D, 16.0D, 5.0D, 2.0D, direction), IBooleanFunction.OR);
                    newShape = VoxelShapes.combineAndSimplify(newShape, ShapeHelper.makeCuboidShape(2.0D, 3.0D, 14.0D, 16.0D, 5.0D, 16.0D, direction), IBooleanFunction.OR);
                    shapes[i] = newShape;
                    break;
                case TURN_RIGHT:
                    newShape = VoxelShapes.combineAndSimplify(ShapeHelper.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D, direction), ShapeHelper.makeCuboidShape(0.0D, 3.0D, 0.0D, 2.0D, 5.0D, 2.0D, direction), IBooleanFunction.OR);
                    newShape = VoxelShapes.combineAndSimplify(newShape, ShapeHelper.makeCuboidShape(14.0D, 3.0D, 0.0D, 16.0D, 5.0D, 16.0D, direction), IBooleanFunction.OR);
                    newShape = VoxelShapes.combineAndSimplify(newShape, ShapeHelper.makeCuboidShape(0.0D, 3.0D, 14.0D, 14.0D, 5.0D, 16.0D, direction), IBooleanFunction.OR);
                    shapes[i] = newShape;
                    break;
            }
        }
        return shapes;
    }

    public enum ConveyorShape implements IStringSerializable {
        STRAIGHT("straight", 0),
        TURN_LEFT("turn_left", 1),
        TURN_RIGHT("turn_right", 2);

        private String name;
        private int index;

        ConveyorShape(String name, int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public int getIndex() { return index; }

        public static ConveyorShape getFromIndex(int index) {
            for (ConveyorShape shape : ConveyorShape.values()) {
                if (shape.getIndex() == index) { return shape; }
            }
            return STRAIGHT;
        }
    }
}