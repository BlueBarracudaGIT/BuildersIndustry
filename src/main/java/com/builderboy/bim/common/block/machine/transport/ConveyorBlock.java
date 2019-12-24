package com.builderboy.bim.common.block.machine.transport;

import com.builderboy.bim.common.block.machine.MachineBlock;
import com.builderboy.bim.common.block.tileentity.transport.ConveyorTileEntity;
import com.builderboy.bim.util.ShapeHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ConveyorTileEntity();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (world.getTileEntity(pos) instanceof ConveyorTileEntity) {
            ConveyorTileEntity conveyor = (ConveyorTileEntity) world.getTileEntity(pos);

            if (!conveyor.hasItems()) {

            }
        }

        return  false;
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        Direction face = state.get(FACING);

        BlockState baseState = this.getDefaultState();

        BlockPos negX = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
        BlockPos posX = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
        BlockPos negZ = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
        BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);

        Direction negXFace = null;
        Direction posXFace = null;
        Direction negZFace = null;
        Direction posZFace = null;

        if (world.getBlockState(negX).getBlock() instanceof ConveyorBlock) { negXFace = world.getBlockState(negX).get(FACING); }
        if (world.getBlockState(posX).getBlock() instanceof ConveyorBlock) { posXFace = world.getBlockState(posX).get(FACING); }
        if (world.getBlockState(negZ).getBlock() instanceof ConveyorBlock) { negZFace = world.getBlockState(negZ).get(FACING); }
        if (world.getBlockState(posZ).getBlock() instanceof ConveyorBlock) { posZFace = world.getBlockState(posZ).get(FACING); }

        if (!isMoving) {

            switch (face.getAxis()) {
                //North and South
                case Z:
                    if (face == Direction.NORTH) {
                        if (negZFace != null) {
                            if (posZFace != null) {
                                if (posZFace.getAxis() == face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                    return;
                                }
                            }

                            if (negXFace != null) {
                                if (negXFace.getAxis() != face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, Direction.EAST).with(SHAPE, ConveyorShape.TURN_LEFT));
                                    return;
                                }
                            }

                            if (posXFace != null) {
                                if (posXFace.getAxis() != face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, Direction.WEST).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                    return;
                                }
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                            return;
                        }

                        if (negXFace != null && posXFace != null) {
                            //Straight
                            if (negXFace.getAxis() == face.getAxis() && posXFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            //Left
                            if (negXFace.getAxis() != face.getAxis() && posXFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT));
                                return;
                            }

                            //Right
                            if (negXFace.getAxis() == face.getAxis() && posXFace.getAxis() != face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                return;
                            }
                        } else if (negXFace != null) {
                            if (negXFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            if (negXFace == Direction.EAST) {
                                world.setBlockState(pos, baseState.with(FACING, Direction.EAST).with(SHAPE, ConveyorShape.TURN_LEFT));
                                return;
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT));
                            return;
                        } else if (posXFace != null) {
                            if (posXFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            if (posXFace == Direction.WEST) {
                                world.setBlockState(pos, baseState.with(FACING, Direction.WEST).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                return;
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT));
                            return;
                        }
                    }

                    if (face == Direction.SOUTH) {
                        if (posZFace != null) {
                            if (negZFace != null) {
                                if (negZFace.getAxis() == face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                    return;
                                }
                            }

                            if (posXFace != null) {
                                if (posXFace.getAxis() != face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, Direction.WEST).with(SHAPE, ConveyorShape.TURN_LEFT));
                                    return;
                                }
                            }

                            if (negXFace != null) {
                                if (negXFace.getAxis() != face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, Direction.EAST).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                    return;
                                }
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                            return;
                        }

                        if (negXFace != null && posXFace != null) {
                            //Straight
                            if (negXFace.getAxis() == face.getAxis() && posXFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            //Left
                            if (negXFace.getAxis() == face.getAxis() && posXFace.getAxis() != face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT));
                                return;
                            }

                            //Right
                            if (negXFace.getAxis() != face.getAxis() && posXFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                return;
                            }
                        } else if (negXFace != null) {
                            if (negXFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            if (negXFace == Direction.EAST) {
                                world.setBlockState(pos, baseState.with(FACING, Direction.EAST).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                return;
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT));
                            return;
                        } else if (posXFace != null) {
                            if (posXFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            if (posXFace == Direction.WEST) {
                                world.setBlockState(pos, baseState.with(FACING, Direction.WEST).with(SHAPE, ConveyorShape.TURN_LEFT));
                                return;
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT));
                            return;
                        }
                    }

                    break;
                case X:
                    if  (face == Direction.EAST) {
                        if (posXFace != null) {
                            if (negXFace != null) {
                                if (negXFace.getAxis() == face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                    return;
                                }
                            }

                            if (negZFace != null) {
                                if (negZFace.getAxis() != face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, Direction.SOUTH).with(SHAPE, ConveyorShape.TURN_LEFT));
                                    return;
                                }
                            }

                            if (posZFace != null) {
                                if (posZFace.getAxis() == face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, Direction.NORTH).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                    return;
                                }
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                            return;
                        }

                        if (negZFace != null && posZFace != null) {
                            //Straight
                            if (negZFace.getAxis() == face.getAxis() && posZFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            //Left
                            if (negZFace.getAxis() != face.getAxis() && posZFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT));
                                return;
                            }

                            //Right
                            if (negZFace.getAxis() == face.getAxis() && posZFace.getAxis() != face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                return;
                            }
                        } else if (negZFace != null) {
                            if (negZFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            if (negZFace == Direction.SOUTH) {
                                world.setBlockState(pos, baseState.with(FACING, Direction.SOUTH).with(SHAPE, ConveyorShape.TURN_LEFT));
                                return;
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT));
                            return;
                        } else if (posZFace != null) {
                            if (posZFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            if (posZFace == Direction.NORTH) {
                                world.setBlockState(pos, baseState.with(FACING, Direction.NORTH).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                return;
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT));
                            return;
                        }
                    }

                    if (face == Direction.WEST) {
                        if (negXFace != null) {
                            if (posXFace != null) {
                                if (posXFace.getAxis() == face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                    return;
                                }
                            }

                            if (posZFace != null) {
                                if (posZFace.getAxis() == face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, Direction.NORTH).with(SHAPE, ConveyorShape.TURN_LEFT));
                                    return;
                                }
                            }

                            if (negZFace != null) {
                                if (negZFace.getAxis() != face.getAxis()) {
                                    world.setBlockState(pos, baseState.with(FACING, Direction.SOUTH).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                    return;
                                }
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                            return;
                        }

                        if (negZFace != null && posZFace != null) {
                            //Straight
                            if (negZFace.getAxis() == face.getAxis() && posZFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            //Left
                            if (negZFace.getAxis() == face.getAxis() && posZFace.getAxis() != face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT));
                                return;
                            }

                            //Right
                            if (negZFace.getAxis() != face.getAxis() && posZFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                return;
                            }
                        } else if (negZFace != null) {
                            if (negZFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            if (negZFace == Direction.SOUTH) {
                                world.setBlockState(pos, baseState.with(FACING, Direction.SOUTH).with(SHAPE, ConveyorShape.TURN_RIGHT));
                                return;
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_RIGHT));
                            return;
                        } else if (posZFace != null) {
                            if (posZFace.getAxis() == face.getAxis()) {
                                world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
                                return;
                            }

                            if (posZFace == Direction.NORTH) {
                                world.setBlockState(pos, baseState.with(FACING, Direction.NORTH).with(SHAPE, ConveyorShape.TURN_LEFT));
                                return;
                            }

                            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.TURN_LEFT));
                            return;
                        }
                    }
                    break;
            }

            world.setBlockState(pos, baseState.with(FACING, face).with(SHAPE, ConveyorShape.STRAIGHT));
        }
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.IGNORE;
    }

    private VoxelShape getConveyorVoxel(BlockState state, IBlockReader world, BlockPos pos) {
        Direction face = state.get(FACING);

        BlockPos negX = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
        BlockPos posX = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
        BlockPos negZ = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
        BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);

        Direction negXFace = null;
        Direction posXFace = null;
        Direction negZFace = null;
        Direction posZFace = null;

        if (world.getBlockState(negX).getBlock() instanceof ConveyorBlock) { negXFace = world.getBlockState(negX).get(FACING); }
        if (world.getBlockState(posX).getBlock() instanceof ConveyorBlock) { posXFace = world.getBlockState(posX).get(FACING); }
        if (world.getBlockState(negZ).getBlock() instanceof ConveyorBlock) { negZFace = world.getBlockState(negZ).get(FACING); }
        if (world.getBlockState(posZ).getBlock() instanceof ConveyorBlock) { posZFace = world.getBlockState(posZ).get(FACING); }

        switch (face.getAxis()) {
            //North and South
            case Z:
                if (face == Direction.NORTH) {
                    if (negZFace != null) {
                        if (posZFace != null) {
                            if (posZFace.getAxis() == face.getAxis()) {
                                return SHAPES[face.getHorizontalIndex()];
                            }
                        }

                        if (negXFace != null) {
                            if (negXFace.getAxis() != face.getAxis()) {
                                return SHAPES[getShapeIndex(Direction.EAST, ConveyorShape.TURN_LEFT)];
                            }
                        }

                        if (posXFace != null) {
                            if (posXFace.getAxis() != face.getAxis()) {
                                return SHAPES[getShapeIndex(Direction.WEST, ConveyorShape.TURN_RIGHT)];
                            }
                        }

                        return SHAPES[face.getHorizontalIndex()];
                    }

                    if (negXFace != null && posXFace != null) {
                        //Straight
                        if (negXFace.getAxis() == face.getAxis() && posXFace.getAxis() == face.getAxis()) {
                            return SHAPES[face.getHorizontalIndex()];
                        }

                        //Left
                        if (negXFace.getAxis() != face.getAxis() && posXFace.getAxis() == face.getAxis()) {
                            return SHAPES[getShapeIndex(face, ConveyorShape.TURN_LEFT)];
                        }

                        //Right
                        if (negXFace.getAxis() == face.getAxis() && posXFace.getAxis() != face.getAxis()) {
                            return SHAPES[getShapeIndex(face, ConveyorShape.TURN_RIGHT)];
                        }
                    } else if (negXFace != null) {
                        if (negXFace.getAxis() == face.getAxis()) { return SHAPES[face.getHorizontalIndex()]; }
                        if (negXFace == Direction.EAST) { return SHAPES[getShapeIndex(Direction.EAST, ConveyorShape.TURN_LEFT)]; }

                        return SHAPES[getShapeIndex(face, ConveyorShape.TURN_LEFT)];
                    } else if (posXFace != null) {
                        if (posXFace.getAxis() == face.getAxis()) { return SHAPES[face.getHorizontalIndex()]; }
                        if (posXFace == Direction.WEST) { return SHAPES[getShapeIndex(Direction.WEST, ConveyorShape.TURN_RIGHT)]; }

                        return SHAPES[getShapeIndex(face, ConveyorShape.TURN_RIGHT)];
                    }
                }

                if (face == Direction.SOUTH) {
                    if (posZFace != null) {
                        if (negZFace != null) {
                            if (negZFace.getAxis() == face.getAxis()) {
                                return SHAPES[face.getHorizontalIndex()];
                            }
                        }

                        if (posXFace != null) {
                            if (posXFace.getAxis() != face.getAxis()) {
                                return SHAPES[getShapeIndex(Direction.WEST, ConveyorShape.TURN_LEFT)];
                            }
                        }

                        if (negXFace != null) {
                            if (negXFace.getAxis() != face.getAxis()) {
                                return SHAPES[getShapeIndex(Direction.EAST, ConveyorShape.TURN_RIGHT)];
                            }
                        }

                        return SHAPES[face.getHorizontalIndex()];
                    }

                    if (negXFace != null && posXFace != null) {
                        //Straight
                        if (negXFace.getAxis() == face.getAxis() && posXFace.getAxis() == face.getAxis()) {
                            return SHAPES[face.getHorizontalIndex()];
                        }

                        //Left
                        if (negXFace.getAxis() == face.getAxis() && posXFace.getAxis() != face.getAxis()) {
                            return SHAPES[getShapeIndex(face, ConveyorShape.TURN_LEFT)];
                        }

                        //Right
                        if (negXFace.getAxis() != face.getAxis() && posXFace.getAxis() == face.getAxis()) {
                            return SHAPES[getShapeIndex(face, ConveyorShape.TURN_RIGHT)];
                        }
                    } else if (negXFace != null) {
                        if (negXFace.getAxis() == face.getAxis()) { return SHAPES[face.getHorizontalIndex()]; }
                        if (negXFace == Direction.EAST) { return SHAPES[getShapeIndex(Direction.EAST, ConveyorShape.TURN_RIGHT)]; }

                        return SHAPES[getShapeIndex(face, ConveyorShape.TURN_RIGHT)];
                    } else if (posXFace != null) {
                        if (posXFace.getAxis() == face.getAxis()) { return SHAPES[face.getHorizontalIndex()]; }
                        if (posXFace == Direction.WEST) { return SHAPES[getShapeIndex(Direction.WEST, ConveyorShape.TURN_LEFT)]; }

                        return SHAPES[getShapeIndex(face, ConveyorShape.TURN_LEFT)];
                    }
                }

                break;
            case X:
                if  (face == Direction.EAST) {
                    if (posXFace != null) {
                        if (negXFace != null) {
                            if (negXFace.getAxis() == face.getAxis()) {
                                return SHAPES[face.getHorizontalIndex()];
                            }
                        }

                        if (negZFace != null) {
                            if (negZFace.getAxis() != face.getAxis()) {
                                return SHAPES[getShapeIndex(Direction.SOUTH, ConveyorShape.TURN_LEFT)];
                            }
                        }

                        if (posZFace != null) {
                            if (posZFace.getAxis() == face.getAxis()) {
                                return SHAPES[getShapeIndex(Direction.NORTH, ConveyorShape.TURN_RIGHT)];
                            }
                        }

                        return SHAPES[face.getHorizontalIndex()];
                    }

                    if (negZFace != null && posZFace != null) {
                        //Straight
                        if (negZFace.getAxis() == face.getAxis() && posZFace.getAxis() == face.getAxis()) {
                            return SHAPES[face.getHorizontalIndex()];
                        }

                        //Left
                        if (negZFace.getAxis() != face.getAxis() && posZFace.getAxis() == face.getAxis()) {
                            return SHAPES[getShapeIndex(face, ConveyorShape.TURN_LEFT)];
                        }

                        //Right
                        if (negZFace.getAxis() == face.getAxis() && posZFace.getAxis() != face.getAxis()) {
                            return SHAPES[getShapeIndex(face, ConveyorShape.TURN_RIGHT)];
                        }
                    } else if (negZFace != null) {
                        if (negZFace.getAxis() == face.getAxis()) { return SHAPES[face.getHorizontalIndex()]; }
                        if (negZFace == Direction.SOUTH) { return SHAPES[getShapeIndex(Direction.SOUTH, ConveyorShape.TURN_LEFT)]; }

                        return SHAPES[getShapeIndex(face, ConveyorShape.TURN_LEFT)];
                    } else if (posZFace != null) {
                        if (posZFace.getAxis() == face.getAxis()) { return SHAPES[face.getHorizontalIndex()]; }
                        if (posZFace == Direction.NORTH) { return SHAPES[getShapeIndex(Direction.NORTH, ConveyorShape.TURN_RIGHT)]; }

                        return SHAPES[getShapeIndex(face, ConveyorShape.TURN_RIGHT)];
                    }
                }

                if (face == Direction.WEST) {
                    if (negXFace != null) {
                        if (posXFace != null) {
                            if (posXFace.getAxis() == face.getAxis()) {
                                return SHAPES[face.getHorizontalIndex()];
                            }
                        }

                        if (posZFace != null) {
                            if (posZFace.getAxis() == face.getAxis()) {
                                return SHAPES[getShapeIndex(Direction.NORTH, ConveyorShape.TURN_LEFT)];
                            }
                        }

                        if (negZFace != null) {
                            if (negZFace.getAxis() != face.getAxis()) {
                                return SHAPES[getShapeIndex(Direction.SOUTH, ConveyorShape.TURN_RIGHT)];
                            }
                        }

                        return SHAPES[face.getHorizontalIndex()];
                    }

                    if (negZFace != null && posZFace != null) {
                        //Straight
                        if (negZFace.getAxis() == face.getAxis() && posZFace.getAxis() == face.getAxis()) {
                            return SHAPES[face.getHorizontalIndex()];
                        }

                        //Left
                        if (negZFace.getAxis() == face.getAxis() && posZFace.getAxis() != face.getAxis()) {
                            return SHAPES[getShapeIndex(face, ConveyorShape.TURN_LEFT)];
                        }

                        //Right
                        if (negZFace.getAxis() != face.getAxis() && posZFace.getAxis() == face.getAxis()) {
                            return SHAPES[getShapeIndex(face, ConveyorShape.TURN_RIGHT)];
                        }
                    } else if (negZFace != null) {
                        if (negZFace.getAxis() == face.getAxis()) { return SHAPES [face.getHorizontalIndex()]; }
                        if (negZFace == Direction.SOUTH) { return SHAPES[getShapeIndex(Direction.SOUTH, ConveyorShape.TURN_RIGHT)]; }

                        return SHAPES[getShapeIndex(face, ConveyorShape.TURN_RIGHT)];
                    } else if (posZFace != null) {
                        if (posZFace.getAxis() == face.getAxis()) { return SHAPES [face.getHorizontalIndex()]; }
                        if (posZFace == Direction.NORTH) { return SHAPES[getShapeIndex(Direction.NORTH, ConveyorShape.TURN_LEFT)]; }

                        return SHAPES[getShapeIndex(face, ConveyorShape.TURN_LEFT)];
                    }
                }
                break;
        }

        return SHAPES[face.getHorizontalIndex()];
    }

    private int getShapeIndex(Direction face, ConveyorShape shape) {
        switch (shape) {
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